// src/main/java/com/accesscontrol/services/ai/AiSuggestionService.java
package com.accesscontrol.services;

import com.accesscontrol.dto.request.AiSuggestionRequest;
import com.accesscontrol.dto.response.AiSuggestionResponse;

public interface AiSuggestionService {
    AiSuggestionResponse suggest(AiSuggestionRequest req);
}
