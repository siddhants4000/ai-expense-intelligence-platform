package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.AddOrganizationMemberRequest;
import com.example.ai_expense_backend.dto.CreateOrganizationRequest;
import com.example.ai_expense_backend.dto.OrganizationMemberResponse;
import com.example.ai_expense_backend.dto.OrganizationResponse;
import com.example.ai_expense_backend.entity.AppUser;
import com.example.ai_expense_backend.entity.Organization;
import com.example.ai_expense_backend.entity.OrganizationMember;
import com.example.ai_expense_backend.exception.DuplicateResourceException;
import com.example.ai_expense_backend.exception.ResourceNotFoundException;
import com.example.ai_expense_backend.repository.AppUserRepository;
import com.example.ai_expense_backend.repository.OrganizationMemberRepository;
import com.example.ai_expense_backend.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final AppUserRepository appUserRepository;
    private final OrganizationMemberRepository organizationMemberRepository;

    @Override
    public OrganizationResponse createOrganization(CreateOrganizationRequest request) {
        if (organizationRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Organization name already exists");
        }

        Organization organization = Organization.builder()
                .name(request.name())
                .description(request.description())
                .build();

        Organization savedOrganization = organizationRepository.save(organization);
        return mapToResponse(savedOrganization);
    }

    @Override
    public List<OrganizationResponse> getAllOrganizations() {
        return organizationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrganizationResponse getOrganizationById(UUID organizationId) {
        Organization organization = getOrganization(organizationId);
        return mapToResponse(organization);
    }

    @Override
    public OrganizationMemberResponse addMember(UUID organizationId, AddOrganizationMemberRequest request) {
        Organization organization = getOrganization(organizationId);

        AppUser user = appUserRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (organizationMemberRepository.findByOrganizationAndUser(organization, user).isPresent()) {
            throw new DuplicateResourceException("User is already a member of this organization");
        }

        OrganizationMember member = OrganizationMember.builder()
                .organization(organization)
                .user(user)
                .role(request.role())
                .build();

        OrganizationMember savedMember = organizationMemberRepository.save(member);
        return mapToMemberResponse(savedMember);
    }

    @Override
    public List<OrganizationMemberResponse> getMembers(UUID organizationId) {
        Organization organization = getOrganization(organizationId);

        return organizationMemberRepository.findByOrganization(organization)
                .stream()
                .map(this::mapToMemberResponse)
                .toList();
    }

    private Organization getOrganization(UUID organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
    }

    private OrganizationResponse mapToResponse(Organization organization) {
        return new OrganizationResponse(
                organization.getId(),
                organization.getName(),
                organization.getDescription(),
                organization.getCreatedAt()
        );
    }

    private OrganizationMemberResponse mapToMemberResponse(OrganizationMember member) {
        return new OrganizationMemberResponse(
                member.getId(),
                member.getOrganization().getId(),
                member.getOrganization().getName(),
                member.getUser().getId(),
                member.getUser().getEmail(),
                member.getRole(),
                member.getJoinedAt()
        );
    }
}