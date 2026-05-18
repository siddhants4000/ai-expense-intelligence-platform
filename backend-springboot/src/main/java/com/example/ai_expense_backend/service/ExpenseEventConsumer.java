package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.config.KafkaTopicConfig;
import com.example.ai_expense_backend.entity.AuditLog;
import com.example.ai_expense_backend.event.ExpenseCreatedEvent;
import com.example.ai_expense_backend.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ExpenseEventConsumer {

    private final AuditLogRepository auditLogRepository;

    @KafkaListener(
            topics = KafkaTopicConfig.EXPENSE_CREATED_TOPIC,
            groupId = "expense-audit-service"
    )
    public void consumeExpenseCreatedEvent(ExpenseCreatedEvent event) {
        String message = "Expense created: "
                + event.title()
                + " | amount="
                + event.amount()
                + " | category="
                + event.category();

        AuditLog auditLog = AuditLog.builder()
                .eventType("EXPENSE_CREATED")
                .referenceId(event.expenseId())
                .organizationId(event.organizationId())
                .userId(event.createdByUserId())
                .message(message)
                .createdAt(Instant.now())
                .build();

        auditLogRepository.save(auditLog);

        System.out.println("Audit log saved for expense event: " + event.expenseId());
    }
}