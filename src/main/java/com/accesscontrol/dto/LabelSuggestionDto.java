// src/main/java/com/accesscontrol/dto/ai/LabelSuggestionDto.java
package com.accesscontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LabelSuggestionDto {
    private int label;      // e.g., "bug"
    private double confidence; // 0.0–1.0
}
