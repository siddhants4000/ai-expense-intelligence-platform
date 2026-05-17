package com.example.ai_expense_backend.dto;

import java.time.Instant;
import java.util.UUID;

public record OrganizationResponse(
        UUID id,
        String name,
        String description,
        Instant createdAt
) {
}