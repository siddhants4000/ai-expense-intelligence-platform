package com.example.ai_expense_backend.repository;

import com.example.ai_expense_backend.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    Optional<Organization> findByName(String name);

    boolean existsByName(String name);
}