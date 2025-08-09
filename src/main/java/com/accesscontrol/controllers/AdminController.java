package com.accesscontrol.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.dto.request.RegisterRequest;
import com.accesscontrol.dto.response.UserDto;
import com.accesscontrol.mapper.UserMapper;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.UserRepository;
import com.accesscontrol.services.UserService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired private UserRepository userRepository;

    @Autowired private UserService userService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(UserMapper::toDto)
            .toList();
    }
    @DeleteMapping("/users/{userId}")
public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
    userService.deleteUser(userId);
    return ResponseEntity.ok().build();
}

// Update user
@PutMapping("/users/{userId}")
public ResponseEntity<UserDto> updateUser(@PathVariable UUID userId, @RequestBody RegisterRequest request) {
    User updated = userService.updateUser(userId, request);
    return ResponseEntity.ok(UserMapper.toDto(updated));
}

}
