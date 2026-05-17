package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.CreateExpenseRequest;
import com.example.ai_expense_backend.dto.ExpenseResponse;
import com.example.ai_expense_backend.entity.AppUser;
import com.example.ai_expense_backend.entity.Expense;
import com.example.ai_expense_backend.entity.ExpenseCategory;
import com.example.ai_expense_backend.entity.Organization;
import com.example.ai_expense_backend.exception.InvalidRequestException;
import com.example.ai_expense_backend.exception.ResourceNotFoundException;
import com.example.ai_expense_backend.repository.AppUserRepository;
import com.example.ai_expense_backend.repository.ExpenseRepository;
import com.example.ai_expense_backend.repository.OrganizationMemberRepository;
import com.example.ai_expense_backend.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ai_expense_backend.dto.AiAnomalyResponse;
import com.example.ai_expense_backend.dto.AiCategorizationRequest;
import com.example.ai_expense_backend.dto.AiCategorizationResponse;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final OrganizationRepository organizationRepository;
    private final AppUserRepository appUserRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final AiServiceClient aiServiceClient;

    @Override
    public ExpenseResponse createExpense(UUID organizationId, CreateExpenseRequest request) {
        Organization organization = getOrganization(organizationId);
        AppUser createdBy = getUser(request.createdByUserId());

        organizationMemberRepository.findByOrganizationAndUser(organization, createdBy)
                .orElseThrow(() -> new InvalidRequestException("User is not a member of this organization"));

        Expense expense = Expense.builder()
                .title(request.title())
                .description(request.description())
                .amount(request.amount())
                .category(request.category())
                .expenseDate(request.expenseDate())
                .organization(organization)
                .createdBy(createdBy)
                .build();

        Expense savedExpense = expenseRepository.save(expense);

        return mapToResponse(savedExpense);
    }

    @Override
    public Page<ExpenseResponse> getExpensesByOrganization(UUID organizationId, Pageable pageable) {
        Organization organization = getOrganization(organizationId);

        return expenseRepository.findByOrganization(organization, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<ExpenseResponse> getExpensesByCategory(
            UUID organizationId,
            ExpenseCategory category,
            Pageable pageable
    ) {
        Organization organization = getOrganization(organizationId);

        return expenseRepository.findByOrganizationAndCategory(organization, category, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<ExpenseResponse> getExpensesByDateRange(
            UUID organizationId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        Organization organization = getOrganization(organizationId);

        return expenseRepository.findByOrganizationAndExpenseDateBetween(
                        organization,
                        startDate,
                        endDate,
                        pageable
                )
                .map(this::mapToResponse);
    }

    @Override
    public AiCategorizationResponse previewCategory(CreateExpenseRequest request) {
        AiCategorizationRequest aiRequest = new AiCategorizationRequest(
                request.title(),
                request.description(),
                request.amount(),
                request.expenseDate()
        );

        return aiServiceClient.categorizeExpense(aiRequest);
        }

        @Override
        public ExpenseResponse createExpenseWithAiCategory(
                UUID organizationId,
                CreateExpenseRequest request
        ) {
                AiCategorizationResponse aiResponse = previewCategory(request);

                ExpenseCategory predictedCategory = ExpenseCategory.valueOf(
                        aiResponse.predicted_category()
                );

                CreateExpenseRequest updatedRequest = new CreateExpenseRequest(
                        request.title(),
                        request.description(),
                        request.amount(),
                        predictedCategory,
                        request.expenseDate(),
                        request.createdByUserId()
                );

                return createExpense(organizationId, updatedRequest);
        }

        @Override
        public AiAnomalyResponse checkAnomaly(CreateExpenseRequest request) {
        AiCategorizationRequest aiRequest = new AiCategorizationRequest(
                request.title(),
                request.description(),
                request.amount(),
                request.expenseDate()
        );

        return aiServiceClient.detectAnomaly(aiRequest);
        }

    private Organization getOrganization(UUID organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
    }

    private AppUser getUser(UUID userId) {
        return appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getTitle(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getExpenseDate(),
                expense.getOrganization().getId(),
                expense.getOrganization().getName(),
                expense.getCreatedBy().getId(),
                expense.getCreatedBy().getEmail(),
                expense.getCreatedAt()
        );
    }
}