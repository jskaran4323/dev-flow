package com.accesscontrol.controllers;

import com.accesscontrol.dto.request.LabelRequest;
import com.accesscontrol.models.Label;
import com.accesscontrol.services.LabelService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{projectId}/labels")
@RequiredArgsConstructor
public class LabelController {
  private final LabelService labelService;

  @GetMapping
  public ResponseEntity<List<Label>> getLabels(@PathVariable UUID projectId) {
    List<Label> labels = labelService.getLabelByProjectId(projectId);
    return ResponseEntity.ok(labels);
  }

  @PostMapping
  public ResponseEntity<Label> createLabel(
      @PathVariable UUID projectId, @RequestBody LabelRequest request) {
    Label savedLabel = labelService.createLabel(projectId, request);
    return ResponseEntity.ok(savedLabel);
  }
}
