package com.example.ai_expense_backend.controller;

import com.example.ai_expense_backend.dto.ApiResponse;
import com.example.ai_expense_backend.dto.AuditLogResponse;
import com.example.ai_expense_backend.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<AuditLogResponse>> getAllAuditLogs() {
        List<AuditLogResponse> response = auditLogService.getAllAuditLogs();
        return ApiResponse.success("Audit logs fetched successfully", response);
    }
}