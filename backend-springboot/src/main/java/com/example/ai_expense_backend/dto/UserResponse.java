package com.example.ai_expense_backend.dto;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String fullName,
        String email,
        String phoneNumber,
        Instant createdAt
) {
}