package com.example.ai_expense_backend.dto;

import com.example.ai_expense_backend.entity.ExpenseCategory;

import java.math.BigDecimal;

public record CategoryBreakdownResponse(
        ExpenseCategory category,
        BigDecimal totalAmount,
        long transactionCount
) {
}