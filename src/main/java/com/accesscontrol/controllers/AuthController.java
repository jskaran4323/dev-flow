package com.accesscontrol.controllers;

import com.accesscontrol.dto.request.LoginRequest;
import com.accesscontrol.dto.request.RegisterRequest;
import com.accesscontrol.dto.response.LoginResponse;
import com.accesscontrol.models.User;
import com.accesscontrol.services.UserService;
import com.accesscontrol.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userService.findByUsernameOrEmail(request.getIdentifier());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or email");
        }

        User user = userOpt.get();

        if (!new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        String token = jwtUtil.generateTokenFromUserType(user);

        // Set HttpOnly JWT cookie
        // In login
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)          // must be true on Render (HTTPS)
                .path("/")
                .maxAge(Duration.ofDays(1))
                .sameSite("None")      // CRUCIAL: allow cross-site cookies
                .build();


        // Return user info (not token)
        LoginResponse loginResponse = new LoginResponse(user.getUsername(), user.getUserTypeEnum().name());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Clear the cookie
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logout successful");
    }
}
