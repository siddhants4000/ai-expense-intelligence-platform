package com.example.ai_expense_backend.dto;

public record AiSpendingInsightResponse(
        String summary,
        String highest_category,
        String risk_level,
        String recommendation
) {
}