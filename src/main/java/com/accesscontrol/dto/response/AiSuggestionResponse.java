// src/main/java/com/accesscontrol/dto/ai/AiSuggestionResponse.java
package com.accesscontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import com.accesscontrol.dto.LabelSuggestionDto;

@Data
@AllArgsConstructor
public class AiSuggestionResponse {
    private List<LabelSuggestionDto> suggestions;
    private boolean success;
    private String message;
    
    // Constructor for success case
    public AiSuggestionResponse(List<LabelSuggestionDto> suggestions) {
        this.suggestions = suggestions;
        this.success = true;
        this.message = "Suggestions generated successfully";
    }
    
    // Static method for error case
    public static AiSuggestionResponse error(String message) {
        return new AiSuggestionResponse(List.of(), false, message);
    }
}
