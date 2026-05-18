package com.example.ai_expense_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String eventType;

    private UUID referenceId;

    private UUID organizationId;

    private UUID userId;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Instant createdAt;
}