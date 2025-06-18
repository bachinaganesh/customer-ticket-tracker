package com.example.tickettracker.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String commentId;
    private String commentDescription;
    private LocalDateTime createdAt;

    // This is comment class a ticket can have lot of comments
    // comment can be created a customer of this ticket or agent of this ticket others won't all for this
    
}
