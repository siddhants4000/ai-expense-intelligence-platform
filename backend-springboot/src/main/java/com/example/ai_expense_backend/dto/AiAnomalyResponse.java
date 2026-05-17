package com.example.ai_expense_backend.dto;

public record AiAnomalyResponse(
        boolean anomaly,
        double risk_score,
        String reason
) {
}