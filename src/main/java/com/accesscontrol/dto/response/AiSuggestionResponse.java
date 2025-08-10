package com.accesscontrol.dto.response;

import com.accesscontrol.dto.LabelSuggestionDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AiSuggestionResponse {
    private List<LabelSuggestionDto> suggestions;
    private boolean success;
    private String message;

    public static AiSuggestionResponse ok(List<LabelSuggestionDto> list) {
        return new AiSuggestionResponse(list, true, "Suggestions generated successfully");
    }

    public static AiSuggestionResponse error(String msg) {
        return new AiSuggestionResponse(List.of(), false, msg);
    }
}
