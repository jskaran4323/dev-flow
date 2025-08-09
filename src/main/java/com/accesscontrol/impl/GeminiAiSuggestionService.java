// Updated Service to map AI labels to your numeric system
package com.accesscontrol.impl;

import com.accesscontrol.dto.LabelSuggestionDto;
import com.accesscontrol.dto.request.AiSuggestionRequest;
import com.accesscontrol.dto.response.AiSuggestionResponse;
import com.accesscontrol.services.AiSuggestionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import com.accesscontrol.enums.LabelType;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiAiSuggestionService implements AiSuggestionService {

    private final WebClient googleWebClient;
    private final ObjectMapper objectMapper;
    
    @Value("${gemini.api.key}")
    private String apiKey;
    
    @Value("${gemini.model:gemini-2.0-flash}")
    private String model;
    
    @Value("${gemini.timeout:20}")
    private int timeoutSeconds;

    // Mapping from AI string labels to your LabelType enum
    private static final Map<String, LabelType> LABEL_MAPPING = Map.of(
        "bug", LabelType.BUG,
        "feature", LabelType.FEATURE,
        "enhancement", LabelType.ENHANCEMENT,
        "documentation", LabelType.DOCUMENTATION,
        "design", LabelType.DESIGN,
        "test", LabelType.TEST,
        "performance", LabelType.PERFORMANCE,
        "security", LabelType.SECURITY,
        "backend", LabelType.BACKEND,
        "frontend", LabelType.FRONTEND
    );
    
   
   
    @Override
    public AiSuggestionResponse suggest(AiSuggestionRequest request) {
        try {
            log.info("Generating AI suggestions for title: {}", request.getTitle());
            
            String prompt = buildPrompt(request);
            JsonNode response = callGeminiApi(prompt);
            List<LabelSuggestionDto> aiSuggestions = parseResponse(response);
            
            // Convert AI suggestions to numeric labels
            List<LabelSuggestionDto> mappedSuggestions = mapToNumericLabels(aiSuggestions);
            
            log.info("Generated {} mapped suggestions", mappedSuggestions.size());
            return new AiSuggestionResponse(mappedSuggestions);
            
        } catch (WebClientException e) {
            log.error("Error calling Gemini API: {}", e.getMessage());
            return AiSuggestionResponse.error("Failed to connect to AI service");
        } catch (Exception e) {
            log.error("Unexpected error generating suggestions", e);
            return AiSuggestionResponse.error("An unexpected error occurred");
        }
    }

    private String buildPrompt(AiSuggestionRequest request) {
        return String.format(
            "You are a label recommender for an issue tracker. " +
            "Analyze the following issue and suggest up to 5 relevant labels from this list: " +
            "bug, feature, enhancement, documentation, urgent, design, test, performance, security, backend, frontend, mobile, ui, api, database. " +
            "Return ONLY a JSON array in this exact format: " +
            "[{\"label\":\"bug\",\"confidence\":0.95},{\"label\":\"frontend\",\"confidence\":0.80}]\n\n" +
            "TITLE: %s\n" +
            "DESCRIPTION: %s\n\n" +
            "JSON Response:",
            request.getTitle(),
            request.getDescription() != null ? request.getDescription() : ""
        );
    }

    private List<LabelSuggestionDto> mapToNumericLabels(List<LabelSuggestionDto> aiSuggestions) {
        return aiSuggestions.stream()
            .map(suggestion -> {
                String aiLabel =  String.valueOf(suggestion.getLabel()).toLowerCase().trim();
                
                // Try primary mapping first
                LabelType labelType = LABEL_MAPPING.get(aiLabel);
                
                // If not found, try additional mappings
              
                
                if (labelType != null) {
                    // Use the enum's ordinal value (which matches your numeric system)
                    return new LabelSuggestionDto(labelType.ordinal(), suggestion.getConfidence());
                } else {
                    log.warn("Unknown AI label '{}', skipping", aiLabel);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .distinct() // Remove duplicates
            .limit(5)   // Limit to 5 suggestions max
            .collect(Collectors.toList());
    }

    private JsonNode callGeminiApi(String prompt) {
        var payload = Map.of(
            "contents", List.of(Map.of(
                "parts", List.of(Map.of("text", prompt))
            )),
            "generationConfig", Map.of(
                "temperature", 0.2,
                "maxOutputTokens", 1000
            )
        );

        return googleWebClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1beta/models/{model}:generateContent")
                .queryParam("key", apiKey)
                .build(model))
            .bodyValue(payload)
            .retrieve()
            .bodyToMono(JsonNode.class)
            .timeout(Duration.ofSeconds(timeoutSeconds))
            .block();
    }

    private List<LabelSuggestionDto> parseResponse(JsonNode response) {
        try {
            String text = extractResponseText(response);
            String jsonArray = extractJsonArray(text);
            
            return objectMapper.readValue(jsonArray, 
                new TypeReference<List<LabelSuggestionDto>>(){});
                
        } catch (Exception e) {
            log.warn("Failed to parse AI response, returning empty list", e);
            return List.of();
        }
    }

    private String extractResponseText(JsonNode response) {
        return response.path("candidates").get(0)
            .path("content").path("parts").get(0)
            .path("text").asText("[]");
    }

    private String extractJsonArray(String text) {
        int start = text.indexOf('[');
        int end = text.lastIndexOf(']') + 1;
        
        if (start >= 0 && end > start) {
            return text.substring(start, end);
        }
        
        return "[]";
    }
}