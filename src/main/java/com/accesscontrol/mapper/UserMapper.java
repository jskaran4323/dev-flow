package com.accesscontrol.mapper;

import com.accesscontrol.dto.response.UserDto;
import com.accesscontrol.models.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
            .userId(user.getId())
            .fullName(user.getFullname())
            .username(user.getUsername())
            .email(user.getEmail())
            .userType(user.getUserTypeEnum())
            .createdAt(user.getCreatedAt())
            .build();
    }
}

