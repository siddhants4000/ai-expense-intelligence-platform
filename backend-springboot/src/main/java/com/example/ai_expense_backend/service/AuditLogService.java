package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.AuditLogResponse;

import java.util.List;

public interface AuditLogService {

    List<AuditLogResponse> getAllAuditLogs();
}