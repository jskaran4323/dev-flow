package com.accesscontrol.services;

import com.accesscontrol.enums.UserType;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    


    public User registerUser(String username,String email, String password, String fullName, UserType userTypeEnum){
        if (userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Username already exists");
        }
              User user = User.builder()
              .username(username).
              password(passwordEncoder.encode(password)).fullname(fullName).email(email).userType(userTypeEnum.getValue()).build();
             System.out.println("saved username and email:"+username+email);
              return userRepository.save(user);

   

    }
    public List<User> getUser(){
       return userRepository.findAll();
    }

    public User userById(UUID id){
        return userRepository.findById(id).orElse(new User());
     }
    
     public List<User> getAllCustomers(int type){
        return userRepository.findByUserType(type);
     }

       public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

     public Optional<User> findByUsernameOrEmail(String identifier) {
        return userRepository.findByUsername(identifier)
               .or(() -> userRepository.findByEmail(identifier));
    }
}