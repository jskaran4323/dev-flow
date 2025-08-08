package com.accesscontrol.controllers;

import com.accesscontrol.dto.response.UserDto;
import com.accesscontrol.mapper.UserMapper;
import com.accesscontrol.models.User;
import com.accesscontrol.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserDto getMyDetails(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserMapper.toDto(user);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getUser()
                          .stream()
                          .map(UserMapper::toDto)
                          .toList();
    }
}
