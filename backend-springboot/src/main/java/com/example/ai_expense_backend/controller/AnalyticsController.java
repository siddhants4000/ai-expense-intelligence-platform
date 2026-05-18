package com.example.ai_expense_backend.controller;

import com.example.ai_expense_backend.dto.ApiResponse;
import com.example.ai_expense_backend.dto.CategoryBreakdownResponse;
import com.example.ai_expense_backend.dto.ExpenseSummaryResponse;
import com.example.ai_expense_backend.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/summary")
    public ApiResponse<ExpenseSummaryResponse> getExpenseSummary(
            @PathVariable UUID organizationId
    ) {
        ExpenseSummaryResponse response =
                analyticsService.getExpenseSummary(organizationId);

        return ApiResponse.success("Expense summary fetched successfully", response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/category-breakdown")
    public ApiResponse<List<CategoryBreakdownResponse>> getCategoryBreakdown(
            @PathVariable UUID organizationId
    ) {
        List<CategoryBreakdownResponse> response =
                analyticsService.getCategoryBreakdown(organizationId);

        return ApiResponse.success("Category breakdown fetched successfully", response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/ai-insights")
    public ApiResponse<?> getAiSpendingInsights(
            @PathVariable UUID organizationId
    ) {
        return ApiResponse.success(
                "AI spending insights fetched successfully",
                analyticsService.getAiSpendingInsights(organizationId)
        );
    }
}