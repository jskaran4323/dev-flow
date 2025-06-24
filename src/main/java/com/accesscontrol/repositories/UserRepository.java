package com.accesscontrol.repositories;

import com.accesscontrol.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.comments WHERE u.id = :id")
    Optional<User> findByIdWithComments(@Param("id") UUID id);

    List<User> findByUserType(int userType);
 }
