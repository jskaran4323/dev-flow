package com.accesscontrol.controllers;
import com.accesscontrol.dto.request.LoginRequest;
import com.accesscontrol.dto.response.LoginResponse;
import com.accesscontrol.dto.response.RegisterRequest;
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
        return userService.registerUser(request.getUsername(),request.getEmail(),request.getPassword(),request.getFullName(),request.getType());
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        Optional<User> userOpt = userService.findByUsernameOrEmail(request.getIdentifier());
    if (userOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/email");
    }

    User user = userOpt.get();
    

    if (!new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid  or password");
    }
    String token = jwtUtil.generateTokenFromUserType(user);
    
    return ResponseEntity.ok(new LoginResponse(token));
    }
    
    

    @GetMapping("/logout")
     public ResponseEntity<?> logout(){
        return ResponseEntity.ok("logout success, token is still there");
     }
    
}
