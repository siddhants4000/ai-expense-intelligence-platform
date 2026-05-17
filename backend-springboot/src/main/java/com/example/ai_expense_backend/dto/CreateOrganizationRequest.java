package com.example.ai_expense_backend.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateOrganizationRequest(

        @NotBlank(message = "Organization name is required")
        String name,

        String description
) {
}