package com.licenta.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public OpenAiService(@Value("${openai.api.key}") String apiKey) {
        System.out.println("Cheia API primită este: " + apiKey);
        this.webClient = WebClient.builder()

                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public String getChatCompletion(String prompt) {
        try {
            JsonNode json = webClient.post()
                    .bodyValue(Map.of(
                            "model", "gpt-3.5-turbo",
                            "messages", List.of(Map.of("role", "user", "content", prompt)),
                            "temperature", 0.7
                    ))
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block(); // blocăm sincronic

            return json.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            return "A apărut o eroare la comunicarea cu OpenAI: " + e.getMessage();
        }
    }

    public String getNaturalLanguageResponse(String context) {
        try {
            JsonNode json = webClient.post()
                    .bodyValue(Map.of(
                            "model", "gpt-3.5-turbo",
                            "messages", List.of(
                                    Map.of("role", "system", "content", "Scrie un răspuns amabil și turistic folosind informațiile primite."),
                                    Map.of("role", "user", "content", context)
                            ),
                            "temperature", 0.7
                    ))
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                            .filter(this::isRetryableError))
                    .block();

            if (json.has("choices") && json.get("choices").size() > 0) {
                return json.path("choices").get(0).path("message").path("content").asText();
            } else {
                return "Nu am găsit un răspuns valid de la OpenAI.";
            }
        } catch (Exception e) {
            return "A apărut o eroare la comunicarea cu OpenAI: " + e.getMessage();
        }
        
    }

    private boolean isRetryableError(Throwable throwable) {
        if (throwable instanceof org.springframework.web.reactive.function.client.WebClientResponseException webEx) {
            return webEx.getStatusCode().value() == 429;
        }
        return false;
    }


}
