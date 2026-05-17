package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.config.KafkaTopicConfig;
import com.example.ai_expense_backend.event.ExpenseCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseEventProducer {

    private final KafkaTemplate<String, ExpenseCreatedEvent> kafkaTemplate;

    public void publishExpenseCreatedEvent(ExpenseCreatedEvent event) {
        kafkaTemplate.send(
                KafkaTopicConfig.EXPENSE_CREATED_TOPIC,
                event.expenseId().toString(),
                event
        );
    }
}