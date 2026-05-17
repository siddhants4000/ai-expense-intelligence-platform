package com.example.ai_expense_backend.dto;

import java.math.BigDecimal;

public record AiSpendingInsightRequest(
        BigDecimal totalAmount,
        long totalExpenses,
        String highestCategory
) {
}