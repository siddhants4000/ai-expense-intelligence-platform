package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.AuditLogResponse;
import com.example.ai_expense_backend.entity.AuditLog;
import com.example.ai_expense_backend.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public List<AuditLogResponse> getAllAuditLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AuditLogResponse mapToResponse(AuditLog auditLog) {
        return new AuditLogResponse(
                auditLog.getId(),
                auditLog.getEventType(),
                auditLog.getReferenceId(),
                auditLog.getOrganizationId(),
                auditLog.getUserId(),
                auditLog.getMessage(),
                auditLog.getCreatedAt()
        );
    }
}