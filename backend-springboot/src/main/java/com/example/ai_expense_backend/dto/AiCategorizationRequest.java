package com.example.ai_expense_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AiCategorizationRequest(
        String title,
        String description,
        BigDecimal amount,
        LocalDate expenseDate
) {
}