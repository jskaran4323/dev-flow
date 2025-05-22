package com.accesscontrol.controllers;
import com.accesscontrol.models.User;
import com.accesscontrol.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
        private UserService userService;
    @GetMapping("/me")
    public User getmyDetails(Authentication authentication){
        return (User) authentication.getPrincipal();
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUser(); 
    }
    @GetMapping("/user")
    public User getUserById(@RequestParam UUID id){
        return userService.userById(id);
    }
    
}
