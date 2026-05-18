package com.example.ai_expense_backend.controller;

import com.example.ai_expense_backend.dto.AddOrganizationMemberRequest;
import com.example.ai_expense_backend.dto.ApiResponse;
import com.example.ai_expense_backend.dto.CreateOrganizationRequest;
import com.example.ai_expense_backend.dto.OrganizationMemberResponse;
import com.example.ai_expense_backend.dto.OrganizationResponse;
import com.example.ai_expense_backend.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<OrganizationResponse> createOrganization(
            @Valid @RequestBody CreateOrganizationRequest request
    ) {
        OrganizationResponse response = organizationService.createOrganization(request);
        return ApiResponse.success("Organization created successfully", response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping
    public ApiResponse<List<OrganizationResponse>> getAllOrganizations() {
        List<OrganizationResponse> response = organizationService.getAllOrganizations();
        return ApiResponse.success("Organizations fetched successfully", response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/{organizationId}")
    public ApiResponse<OrganizationResponse> getOrganizationById(
            @PathVariable UUID organizationId
    ) {
        OrganizationResponse response = organizationService.getOrganizationById(organizationId);
        return ApiResponse.success("Organization fetched successfully", response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{organizationId}/members")
    public ApiResponse<OrganizationMemberResponse> addMember(
            @PathVariable UUID organizationId,
            @Valid @RequestBody AddOrganizationMemberRequest request
    ) {
        OrganizationMemberResponse response = organizationService.addMember(organizationId, request);
        return ApiResponse.success("Organization member added successfully", response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/{organizationId}/members")
    public ApiResponse<List<OrganizationMemberResponse>> getMembers(
            @PathVariable UUID organizationId
    ) {
        List<OrganizationMemberResponse> response = organizationService.getMembers(organizationId);
        return ApiResponse.success("Organization members fetched successfully", response);
    }
}