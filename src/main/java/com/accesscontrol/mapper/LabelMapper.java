package com.accesscontrol.mapper;

import com.accesscontrol.dto.LabelDto;
import com.accesscontrol.models.Label;


public class LabelMapper {
        public static LabelDto toDto(Label label) {
        LabelDto dto = new LabelDto();
        dto.setId(label.getId());
        dto.setType(label.getType());
        return dto;
    }

}
