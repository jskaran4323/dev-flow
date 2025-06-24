package com.accesscontrol.dto;

import com.accesscontrol.enums.LabelType;

import lombok.Data;

@Data
public class LabelRequest {
  private String name;  
  private LabelType type;  
}
