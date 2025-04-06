package com.accesscontrol.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String helloworld(){
        return "smart access control up and running";
    }
}
