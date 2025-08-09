package com.accesscontrol.dto.request;

import java.util.*;

import lombok.Data;


@Data
public class IssueRequest {
    private String title;
    private String description;
    private int status;
    private UUID assigneeId; 
    private List<Integer> labels;
}
