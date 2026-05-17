package com.example.ai_expense_backend.dto;

public record AiCategorizationResponse(
        String predicted_category,
        double confidence,
        String reason
) {
}