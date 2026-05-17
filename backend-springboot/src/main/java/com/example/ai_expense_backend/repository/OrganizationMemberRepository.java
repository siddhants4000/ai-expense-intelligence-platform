package com.example.ai_expense_backend.repository;

import com.example.ai_expense_backend.entity.AppUser;
import com.example.ai_expense_backend.entity.Organization;
import com.example.ai_expense_backend.entity.OrganizationMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationMemberRepository
        extends JpaRepository<OrganizationMember, UUID> {

    List<OrganizationMember> findByOrganization(Organization organization);

    List<OrganizationMember> findByUser(AppUser user);

    Optional<OrganizationMember> findByOrganizationAndUser(
            Organization organization,
            AppUser user
    );
}