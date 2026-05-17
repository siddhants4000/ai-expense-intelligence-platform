package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.AddOrganizationMemberRequest;
import com.example.ai_expense_backend.dto.CreateOrganizationRequest;
import com.example.ai_expense_backend.dto.OrganizationMemberResponse;
import com.example.ai_expense_backend.dto.OrganizationResponse;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {

    OrganizationResponse createOrganization(CreateOrganizationRequest request);

    List<OrganizationResponse> getAllOrganizations();

    OrganizationResponse getOrganizationById(UUID organizationId);

    OrganizationMemberResponse addMember(UUID organizationId, AddOrganizationMemberRequest request);

    List<OrganizationMemberResponse> getMembers(UUID organizationId);
}