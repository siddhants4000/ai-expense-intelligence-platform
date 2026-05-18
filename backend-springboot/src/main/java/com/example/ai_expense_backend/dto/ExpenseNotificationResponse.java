package com.example.ai_expense_backend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ExpenseNotificationResponse(
        String type,
        UUID expenseId,
        UUID organizationId,
        UUID userId,
        String title,
        BigDecimal amount,
        String category,
        String message,
        Instant timestamp
) {
}