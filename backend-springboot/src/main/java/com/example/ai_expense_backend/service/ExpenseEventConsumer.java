package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.config.KafkaTopicConfig;
import com.example.ai_expense_backend.event.ExpenseCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseEventConsumer {

    @KafkaListener(
            topics = KafkaTopicConfig.EXPENSE_CREATED_TOPIC,
            groupId = "expense-audit-service"
    )
    public void consumeExpenseCreatedEvent(ExpenseCreatedEvent event) {
        System.out.println(
                "Expense created event consumed: "
                        + event.expenseId()
                        + " | organizationId="
                        + event.organizationId()
                        + " | amount="
                        + event.amount()
                        + " | category="
                        + event.category()
        );
    }
}