package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.AiAnomalyResponse;
import com.example.ai_expense_backend.dto.AiCategorizationRequest;
import com.example.ai_expense_backend.dto.AiCategorizationResponse;
import com.example.ai_expense_backend.dto.AiSpendingInsightRequest;
import com.example.ai_expense_backend.dto.AiSpendingInsightResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AiServiceClient {

    private static final String AI_SERVICE = "aiService";

    private final RestClient.Builder restClientBuilder;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    @Retry(name = AI_SERVICE)
    @CircuitBreaker(name = AI_SERVICE, fallbackMethod = "categorizeExpenseFallback")
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

    @Retry(name = AI_SERVICE)
    @CircuitBreaker(name = AI_SERVICE, fallbackMethod = "detectAnomalyFallback")
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

    @Retry(name = AI_SERVICE)
    @CircuitBreaker(name = AI_SERVICE, fallbackMethod = "generateSpendingInsightsFallback")
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

    private AiCategorizationResponse categorizeExpenseFallback(
            AiCategorizationRequest request,
            Throwable throwable
    ) {
        return new AiCategorizationResponse(
                "OTHER",
                0.0,
                "AI service temporarily unavailable. Fallback category applied."
        );
    }

    private AiAnomalyResponse detectAnomalyFallback(
            AiCategorizationRequest request,
            Throwable throwable
    ) {
        return new AiAnomalyResponse(
                false,
                0.0,
                "AI service temporarily unavailable. Fallback anomaly result applied."
        );
    }

    private AiSpendingInsightResponse generateSpendingInsightsFallback(
                AiSpendingInsightRequest request,
                Throwable throwable
        ) {
        return new AiSpendingInsightResponse(
                "AI spending insights are temporarily unavailable. Please try again later.",
                "UNKNOWN",
                "LOW",
                "Retry later when AI service becomes available."
        );
        }
}