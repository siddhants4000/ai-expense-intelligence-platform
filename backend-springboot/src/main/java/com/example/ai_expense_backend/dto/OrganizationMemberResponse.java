package com.example.ai_expense_backend.dto;

import com.example.ai_expense_backend.entity.MemberRole;

import java.time.Instant;
import java.util.UUID;

public record OrganizationMemberResponse(
        UUID id,
        UUID organizationId,
        String organizationName,
        UUID userId,
        String userEmail,
        MemberRole role,
        Instant joinedAt
) {
}