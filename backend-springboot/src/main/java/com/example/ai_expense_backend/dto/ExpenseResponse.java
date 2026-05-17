package com.example.ai_expense_backend.dto;

import com.example.ai_expense_backend.entity.ExpenseCategory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponse(
        UUID id,
        String title,
        String description,
        BigDecimal amount,
        ExpenseCategory category,
        LocalDate expenseDate,
        UUID organizationId,
        String organizationName,
        UUID createdByUserId,
        String createdByEmail,
        Instant createdAt
) {
}