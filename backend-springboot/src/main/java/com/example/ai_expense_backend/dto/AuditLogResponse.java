package com.example.ai_expense_backend.dto;

import java.time.Instant;
import java.util.UUID;

public record AuditLogResponse(
        UUID id,
        String eventType,
        UUID referenceId,
        UUID organizationId,
        UUID userId,
        String message,
        Instant createdAt
) {
}