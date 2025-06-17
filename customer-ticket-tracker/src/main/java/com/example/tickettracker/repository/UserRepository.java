package com.example.tickettracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.tickettracker.models.Role;
import com.example.tickettracker.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    
    List<User> findByRole(Role role);
    Optional<User> findByRoleAndUserId(Role role, String userId);
}
