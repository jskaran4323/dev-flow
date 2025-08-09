package com.accesscontrol.impl;

import com.accesscontrol.dto.LabelSuggestionDto;
import com.accesscontrol.dto.request.AiSuggestionRequest;
import com.accesscontrol.dto.response.AiSuggestionResponse;
import com.accesscontrol.services.AiSuggestionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiAiSuggestionService implements AiSuggestionService {

    private final WebClient googleWebClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model:gemini-2.0-flash}")
    private String model;

    @Value("${gemini.timeout:20}")
    private int timeoutSeconds;

    @Override
    public AiSuggestionResponse suggest(AiSuggestionRequest req) {
        try {
            String prompt = buildPrompt(req);
            String text = extractResponseText(callGeminiApi(prompt));
            List<LabelSuggestionDto> suggestions = parseSuggestions(extractJsonArray(text));
            return AiSuggestionResponse.ok(suggestions.stream().distinct().limit(5).toList());
        } catch (Exception e) {
            log.error("AI suggestion error", e);
            return AiSuggestionResponse.error("Failed to generate suggestions");
        }
    }

    private String buildPrompt(AiSuggestionRequest r) {
        return """
            You are a label recommender. Choose up to 5 labels ONLY from:
            0:BUG, 1:FEATURE, 2:ENHANCEMENT, 3:DOCUMENTATION, 4:URGENT, 5:DESIGN,
            6:TEST, 7:PERFORMANCE, 8:SECURITY, 9:BACKEND, 10:FRONTEND.
            Return ONLY JSON: [{"label": 10, "confidence": 0.90}, {"label": 5, "confidence": 0.75}]
            TITLE: %s
            DESCRIPTION: %s
            JSON:
            """.formatted(r.getTitle(), r.getDescription());
    }

    private JsonNode callGeminiApi(String prompt) {
        var payload = mapper.createObjectNode();
    
        var contents = mapper.createArrayNode();
        var user = mapper.createObjectNode();
        var parts = mapper.createArrayNode();
        parts.add(mapper.createObjectNode().put("text", prompt));
        user.set("parts", parts);
        contents.add(user);
        payload.set("contents", contents);
    
        var genConfig = mapper.createObjectNode();
        genConfig.put("temperature", 0.2);
        genConfig.put("maxOutputTokens", 512);
        payload.set("generationConfig", genConfig);
    
        return googleWebClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1beta/models/{model}:generateContent")
                .queryParam("key", apiKey)
                .build(model))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve()
            .bodyToMono(JsonNode.class)
            .timeout(Duration.ofSeconds(timeoutSeconds))
            .block();
    }
    

    private String extractResponseText(JsonNode res) {
        return res.path("candidates").path(0)
                  .path("content").path("parts").path(0)
                  .path("text").asText("[]");
    }

    private String extractJsonArray(String text) {
        int start = text.indexOf('['), end = text.lastIndexOf(']');
        return (start >= 0 && end > start) ? text.substring(start, end + 1) : "[]";
    }

    private List<LabelSuggestionDto> parseSuggestions(String jsonArray) {
        List<LabelSuggestionDto> out = new ArrayList<>();
        try {
            JsonNode arr = mapper.readTree(jsonArray);
            if (arr.isArray()) {
                for (JsonNode n : arr) {
                    int label = n.path("label").isInt()
                        ? n.path("label").asInt()
                        : tryParseInt(n.path("label").asText());
                    if (label >= 0) {
                        double conf = Math.max(0, Math.min(1, n.path("confidence").asDouble(0.5)));
                        out.add(new LabelSuggestionDto(label, conf));
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Parse suggestions failed", e);
        }
        return out;
    }

    private int tryParseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return -1; }
    }
}
