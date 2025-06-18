package com.example.tickettracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.tickettracker.models.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String>{

}
