package com.accesscontrol.controllers;
import com.accesscontrol.dto.RegisterRequest;
import com.accesscontrol.models.User;
import com.accesscontrol.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request){
        return userService.registerUser(request.getUsername(),request.getPassword(),request.getFullName(),request.getRoles());
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUser(); 
    }
    @GetMapping("/user")
    public User getUserById(@RequestParam Long id){
        return userService.userById(id);
    }
}
