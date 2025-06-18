package com.accesscontrol.controllers;
import com.accesscontrol.models.User;
import com.accesscontrol.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private UserService userService;

    @GetMapping("/customers")
    public List<User> getAllCustomers(){
        return userService.getAllCustomers(2);
    }
  
}
