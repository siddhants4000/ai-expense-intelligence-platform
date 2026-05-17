package com.example.ai_expense_backend.dto;

import com.example.ai_expense_backend.entity.MemberRole;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddOrganizationMemberRequest(

        @NotNull(message = "User ID is required")
        UUID userId,

        @NotNull(message = "Role is required")
        MemberRole role
) {
}