package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.AiAnomalyResponse;
import com.example.ai_expense_backend.dto.AiCategorizationResponse;
import com.example.ai_expense_backend.dto.CreateExpenseRequest;
import com.example.ai_expense_backend.dto.ExpenseResponse;
import com.example.ai_expense_backend.entity.ExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface ExpenseService {

    ExpenseResponse createExpense(UUID organizationId, CreateExpenseRequest request);

    ExpenseResponse createExpenseWithAiCategory(UUID organizationId, CreateExpenseRequest request);

    AiCategorizationResponse previewCategory(CreateExpenseRequest request);

    AiAnomalyResponse checkAnomaly(CreateExpenseRequest request);

    Page<ExpenseResponse> getExpensesByOrganization(UUID organizationId, Pageable pageable);

    Page<ExpenseResponse> getExpensesByCategory(
            UUID organizationId,
            ExpenseCategory category,
            Pageable pageable
    );

    Page<ExpenseResponse> getExpensesByDateRange(
            UUID organizationId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}