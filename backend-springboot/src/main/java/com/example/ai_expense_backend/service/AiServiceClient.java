package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.AiAnomalyResponse;
import com.example.ai_expense_backend.dto.AiCategorizationRequest;
import com.example.ai_expense_backend.dto.AiCategorizationResponse;
import com.example.ai_expense_backend.dto.AiSpendingInsightRequest;
import com.example.ai_expense_backend.dto.AiSpendingInsightResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AiServiceClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public AiCategorizationResponse categorizeExpense(
            AiCategorizationRequest request
    ) {
        RestClient restClient = restClientBuilder
                .baseUrl(aiServiceUrl)
                .build();

        return restClient.post()
                .uri("/api/v1/ai/categorize")
                .body(request)
                .retrieve()
                .body(AiCategorizationResponse.class);
    }

    public AiAnomalyResponse detectAnomaly(
                AiCategorizationRequest request
        ) {
        RestClient restClient = restClientBuilder
                .baseUrl(aiServiceUrl)
                .build();

        return restClient.post()
                .uri("/api/v1/ai/anomaly-detection")
                .body(request)
                .retrieve()
                .body(AiAnomalyResponse.class);
        }

        public AiSpendingInsightResponse generateSpendingInsights(
        AiSpendingInsightRequest request
        ) {
                RestClient restClient = restClientBuilder
                        .baseUrl(aiServiceUrl)
                        .build();

                return restClient.post()
                        .uri("/api/v1/ai/spending-insights")
                        .body(request)
                        .retrieve()
                        .body(AiSpendingInsightResponse.class);
        }
}