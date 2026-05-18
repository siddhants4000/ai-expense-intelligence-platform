package com.example.ai_expense_backend.consumer;

import com.example.ai_expense_backend.config.KafkaTopicConfig;
import com.example.ai_expense_backend.event.ExpenseCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExpenseEventDeadLetterConsumer {

    @KafkaListener(
            topics = KafkaTopicConfig.EXPENSE_CREATED_DLT_TOPIC,
            groupId = "audit-log-dlt-group"
    )
    public void consumeDeadLetterEvent(ExpenseCreatedEvent event) {

        log.error("""
                
                DEAD LETTER EVENT RECEIVED
                
                Expense ID: {}
                Title: {}
                Amount: {}
                Category: {}
                
                """,
                event.expenseId(),
                event.title(),
                event.amount(),
                event.category()
        );
    }
}