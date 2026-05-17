package com.example.ai_expense_backend.dto;

import com.example.ai_expense_backend.entity.ExpenseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateExpenseRequest(

        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotNull(message = "Category is required")
        ExpenseCategory category,

        @NotNull(message = "Expense date is required")
        LocalDate expenseDate,

        @NotNull(message = "CreatedBy user ID is required")
        UUID createdByUserId
) {
}