package com.example.ai_expense_backend.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseSummaryResponse(
        UUID organizationId,
        BigDecimal totalAmount,
        long totalExpenses,
        BigDecimal averageAmount
) {
}