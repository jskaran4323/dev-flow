package com.accesscontrol.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class ApproveRequestDto {
    private UUID userId;
}