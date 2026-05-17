package com.example.ai_expense_backend.controller;

import com.example.ai_expense_backend.dto.ApiResponse;
import com.example.ai_expense_backend.dto.CreateExpenseRequest;
import com.example.ai_expense_backend.dto.ExpenseResponse;
import com.example.ai_expense_backend.entity.ExpenseCategory;
import com.example.ai_expense_backend.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ApiResponse<ExpenseResponse> createExpense(
            @PathVariable UUID organizationId,
            @Valid @RequestBody CreateExpenseRequest request
    ) {
        ExpenseResponse response = expenseService.createExpense(organizationId, request);
        return ApiResponse.success("Expense created successfully", response);
    }

    @GetMapping
    public ApiResponse<Page<ExpenseResponse>> getExpensesByOrganization(
            @PathVariable UUID organizationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "expenseDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        PageRequest pageRequest = buildPageRequest(page, size, sortBy, direction);

        Page<ExpenseResponse> response =
                expenseService.getExpensesByOrganization(organizationId, pageRequest);

        return ApiResponse.success("Expenses fetched successfully", response);
    }

    @GetMapping("/category/{category}")
    public ApiResponse<Page<ExpenseResponse>> getExpensesByCategory(
            @PathVariable UUID organizationId,
            @PathVariable ExpenseCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "expenseDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        PageRequest pageRequest = buildPageRequest(page, size, sortBy, direction);

        Page<ExpenseResponse> response =
                expenseService.getExpensesByCategory(organizationId, category, pageRequest);

        return ApiResponse.success("Expenses fetched by category successfully", response);
    }

    @GetMapping("/date-range")
    public ApiResponse<Page<ExpenseResponse>> getExpensesByDateRange(
            @PathVariable UUID organizationId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "expenseDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        PageRequest pageRequest = buildPageRequest(page, size, sortBy, direction);

        Page<ExpenseResponse> response =
                expenseService.getExpensesByDateRange(
                        organizationId,
                        startDate,
                        endDate,
                        pageRequest
                );

        return ApiResponse.success("Expenses fetched by date range successfully", response);
    }

    @PostMapping("/preview-category")
    public ApiResponse<?> previewCategory(
                @PathVariable UUID organizationId,
                @Valid @RequestBody CreateExpenseRequest request
        ) {
        return ApiResponse.success(
                "AI category preview fetched successfully",
                expenseService.previewCategory(request)
        );
        }

        @PostMapping("/auto-categorize")
        public ApiResponse<ExpenseResponse> createExpenseWithAiCategory(
                @PathVariable UUID organizationId,
                @Valid @RequestBody CreateExpenseRequest request
        ) {
        ExpenseResponse response =
                expenseService.createExpenseWithAiCategory(organizationId, request);

        return ApiResponse.success("Expense created with AI category successfully", response);
        }

        @PostMapping("/anomaly-check")
        public ApiResponse<?> checkAnomaly(
                @PathVariable UUID organizationId,
                @Valid @RequestBody CreateExpenseRequest request
        ) {
        return ApiResponse.success(
                "AI anomaly check completed successfully",
                expenseService.checkAnomaly(request)
        );
        }

    private PageRequest buildPageRequest(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = "asc".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return PageRequest.of(page, size, sort);
    }
    
}