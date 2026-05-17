package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.AiSpendingInsightRequest;
import com.example.ai_expense_backend.dto.AiSpendingInsightResponse;
import com.example.ai_expense_backend.dto.CategoryBreakdownResponse;
import com.example.ai_expense_backend.dto.ExpenseSummaryResponse;
import com.example.ai_expense_backend.entity.Expense;
import com.example.ai_expense_backend.entity.ExpenseCategory;
import com.example.ai_expense_backend.entity.Organization;
import com.example.ai_expense_backend.exception.ResourceNotFoundException;
import com.example.ai_expense_backend.repository.ExpenseRepository;
import com.example.ai_expense_backend.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final ExpenseRepository expenseRepository;
    private final OrganizationRepository organizationRepository;
    private final AiServiceClient aiServiceClient;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
        public ExpenseSummaryResponse getExpenseSummary(UUID organizationId) {
        String cacheKey = "expense-summary:" + organizationId;

        try {
                String cachedValue = redisTemplate.opsForValue().get(cacheKey);

                if (cachedValue != null) {
                return objectMapper.readValue(cachedValue, ExpenseSummaryResponse.class);
                }
        } catch (Exception ignored) {
        }

        Organization organization = getOrganization(organizationId);
        List<Expense> expenses = expenseRepository.findByOrganization(organization);

        BigDecimal totalAmount = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalExpenses = expenses.size();

        BigDecimal averageAmount = totalExpenses == 0
                ? BigDecimal.ZERO
                : totalAmount.divide(
                        BigDecimal.valueOf(totalExpenses),
                        2,
                        RoundingMode.HALF_UP
                );

        ExpenseSummaryResponse response = new ExpenseSummaryResponse(
                organization.getId(),
                totalAmount,
                totalExpenses,
                averageAmount
        );

        try {
                redisTemplate.opsForValue().set(
                        cacheKey,
                        objectMapper.writeValueAsString(response),
                        Duration.ofMinutes(10)
                );
        } catch (Exception ignored) {
        }

        return response;
        }

    @Override
    public List<CategoryBreakdownResponse> getCategoryBreakdown(UUID organizationId) {
        Organization organization = getOrganization(organizationId);
        List<Expense> expenses = expenseRepository.findByOrganization(organization);

        Map<ExpenseCategory, CategoryStats> categoryStats = new EnumMap<>(ExpenseCategory.class);

        for (Expense expense : expenses) {
            CategoryStats stats = categoryStats.computeIfAbsent(
                    expense.getCategory(),
                    key -> new CategoryStats()
            );

            stats.totalAmount = stats.totalAmount.add(expense.getAmount());
            stats.transactionCount++;
        }

        return categoryStats.entrySet()
                .stream()
                .map(entry -> new CategoryBreakdownResponse(
                        entry.getKey(),
                        entry.getValue().totalAmount,
                        entry.getValue().transactionCount
                ))
                .toList();
    }

    @Override
        public AiSpendingInsightResponse getAiSpendingInsights(UUID organizationId) {

        Organization organization = getOrganization(organizationId);

        List<Expense> expenses = expenseRepository.findByOrganization(organization);

        BigDecimal totalAmount = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalExpenses = expenses.size();

        Map<ExpenseCategory, BigDecimal> categoryTotals =
                new EnumMap<>(ExpenseCategory.class);

        for (Expense expense : expenses) {
                categoryTotals.merge(
                        expense.getCategory(),
                        expense.getAmount(),
                        BigDecimal::add
                );
        }

        String highestCategory = categoryTotals.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().name())
                .orElse("OTHER");

        AiSpendingInsightRequest request =
                new AiSpendingInsightRequest(
                        totalAmount,
                        totalExpenses,
                        highestCategory
                );

        return aiServiceClient.generateSpendingInsights(request);
        }

    private Organization getOrganization(UUID organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
    }

    private static class CategoryStats {
        private BigDecimal totalAmount = BigDecimal.ZERO;
        private long transactionCount;
    }
}