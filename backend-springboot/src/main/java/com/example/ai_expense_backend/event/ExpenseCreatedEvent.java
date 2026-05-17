package com.example.ai_expense_backend.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseCreatedEvent(
        UUID expenseId,
        UUID organizationId,
        UUID createdByUserId,
        String title,
        BigDecimal amount,
        String category,
        LocalDate expenseDate,
        Instant createdAt
) {
}