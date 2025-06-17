package com.example.tickettracker.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document(collection = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    
    @Id
    private String ticketId;
    private String title;
    private String description;
    private String customerId;
    private String agentId;
    private Status status;
    private List<Comment> comments;
    private LocalDateTime createdAt;

    // This is ticket class which is the main entity of the application
    
}
