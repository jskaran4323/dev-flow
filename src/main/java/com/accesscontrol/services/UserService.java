package com.accesscontrol.services;

import com.accesscontrol.dto.request.RegisterRequest;
import com.accesscontrol.enums.UserType;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    


    public User registerUser(RegisterRequest request){
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        UserType userType = request.getType();
        if (userType == null) {
            userType = UserType.VIEWER;
        }
        if (userType == UserType.ADMIN || userType == UserType.MANAGER) {
            throw new IllegalArgumentException("Cannot assign reserved roles.");
        }
    
              
              User user = User.builder()
              .username(request.getUsername()).
              password(passwordEncoder.encode(request.getPassword())).fullname(request.getFullName()).email(request.getEmail()).userType(userType.getValue()).build();
           
              return userRepository.save(user);

   

    }
    public List<User> getUser(){
       return userRepository.findAll();
    }
    public void deleteUser(UUID userId){
        userRepository.deleteById(userId);

    }
    public User updateUser(UUID userId, RegisterRequest request) {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(request.getUsername());
            user.setFullname(request.getFullName());
            user.setEmail(request.getEmail());
    
            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
    
            if (request.getType() != null) {
                user.setUserType(request.getType().getValue());
            }
    
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
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
     @Override
     public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = findByUsernameOrEmail(usernameOrEmail)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        List.of(user.getUserTypeEnum().toSpringRole()) // explained below
    );
     }
}