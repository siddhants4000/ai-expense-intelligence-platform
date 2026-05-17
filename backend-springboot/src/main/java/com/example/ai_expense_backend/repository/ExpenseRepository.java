package com.example.ai_expense_backend.repository;

import com.example.ai_expense_backend.entity.Expense;
import com.example.ai_expense_backend.entity.ExpenseCategory;
import com.example.ai_expense_backend.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findByOrganization(Organization organization);

    Page<Expense> findByOrganization(Organization organization, Pageable pageable);

    List<Expense> findByOrganizationAndCategory(
            Organization organization,
            ExpenseCategory category
    );

    Page<Expense> findByOrganizationAndCategory(
            Organization organization,
            ExpenseCategory category,
            Pageable pageable
    );

    List<Expense> findByOrganizationAndExpenseDateBetween(
            Organization organization,
            LocalDate startDate,
            LocalDate endDate
    );

    Page<Expense> findByOrganizationAndExpenseDateBetween(
            Organization organization,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}