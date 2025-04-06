package com.accesscontrol.controllers;
import com.accesscontrol.dto.LoginRequest;
import com.accesscontrol.dto.LoginResponse;
import com.accesscontrol.dto.RegisterRequest;
import com.accesscontrol.models.User;
import com.accesscontrol.utils.JwtUtil;
import com.accesscontrol.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request){
        return userService.registerUser(request.getUsername(),request.getPassword(),request.getFullName(),request.getRoles());
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        Optional<User> userOpt = userService.findByUsername(request.getUsername());
    if (userOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    User user = userOpt.get();

    if (!new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    String token = jwtUtil.generateToken(user.getUsername());
    return ResponseEntity.ok(new LoginResponse(token));
    }
    

    @GetMapping("/logout")
     public ResponseEntity<?> logout(){
        return ResponseEntity.ok("logout success, token is still there");
     }
    
}
