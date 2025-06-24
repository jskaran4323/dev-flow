// package com.accesscontrol.services;

// import java.util.List;
// import java.util.Map;
// import org.springframework.beans.factory.annotation.Value;

// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;


// import org.springframework.stereotype.Service;
// import org.springframework.web.reactive.function.client.WebClient;

// import com.fasterxml.jackson.databind.JsonNode;


// @Service
// public class ChatService {

//     @Value("${gemini.api.key}")
//     private String apiKey;
// private final  WebClient webClient;    


// public ChatService(WebClient.Builder builder){
//     this.webClient = builder.baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent").build();
// }
// public String getReplyFromAI(String message) {
//     Map<String, Object> payload = Map.of(
//         "contents", List.of(Map.of("parts", List.of(Map.of("text", message))))
//     );
// // 
//     return webClient.post()
//             .uri(uriBuilder -> uriBuilder.queryParam("key", apiKey).build())
//             .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//             .bodyValue(payload)
//             .retrieve()
//             .bodyToMono(JsonNode.class)
//             .map(json -> json.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText())
//             .block();
// }


// }



