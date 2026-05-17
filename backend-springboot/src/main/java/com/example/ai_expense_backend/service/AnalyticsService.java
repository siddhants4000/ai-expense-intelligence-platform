package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.AiSpendingInsightResponse;
import com.example.ai_expense_backend.dto.CategoryBreakdownResponse;
import com.example.ai_expense_backend.dto.ExpenseSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface AnalyticsService {

    ExpenseSummaryResponse getExpenseSummary(UUID organizationId);

    List<CategoryBreakdownResponse> getCategoryBreakdown(UUID organizationId);

    AiSpendingInsightResponse getAiSpendingInsights(UUID organizationId);
}