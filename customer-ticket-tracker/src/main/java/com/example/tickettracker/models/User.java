package com.example.tickettracker.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdAt;


    // This is for user class to all types of users
        // 1. Customer
        // 2. Agent
        // 3. Admin
        

}
