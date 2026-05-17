package com.example.ai_expense_backend.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String EXPENSE_CREATED_TOPIC = "expense-created-events";

    @Bean
    public NewTopic expenseCreatedTopic() {
        return TopicBuilder.name(EXPENSE_CREATED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}