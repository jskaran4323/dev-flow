package com.accesscontrol.controllers;

import com.accesscontrol.dto.request.AiSuggestionRequest;
import com.accesscontrol.dto.response.AiSuggestionResponse;
import com.accesscontrol.services.AiSuggestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AiSuggestionController {

  private final AiSuggestionService aiSuggestionService;

  /**
   * @param request
   * @return list of suggestion label and confidence
   */
  @PostMapping("/suggest-labels")
  public ResponseEntity<AiSuggestionResponse> suggestLabels(
      @RequestBody AiSuggestionRequest request) {

    log.info("Received label suggestion request for: {}", request.getTitle());

    AiSuggestionResponse response = aiSuggestionService.suggest(request);

    if (response.isSuccess()) {
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.internalServerError().body(response);
    }
  }

  /**
   * @return just a test endpoint to check controller behaviour
   */
  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("AI Suggestion service is running");
  }
}
