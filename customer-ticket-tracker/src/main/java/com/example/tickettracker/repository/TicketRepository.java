package com.example.tickettracker.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.tickettracker.models.Status;
import com.example.tickettracker.models.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String>{

    List<Ticket> findByAgentIdNotNull();
    List<Ticket> findByAgentIdNull();
    List<Ticket> findByStatus(Status status);
}
