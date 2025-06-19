package com.example.tickettracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.tickettracker.models.Status;
import com.example.tickettracker.models.Ticket;
import com.example.tickettracker.repository.TicketRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TicketService {
    
    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        ticket.setStatus(Status.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        log.info("Ticket is stored in db!");
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        log.info("Tickets fetching from db!");
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(String ticketId) {
        log.info("fethcing ticket info from db!");
        return ticketRepository.findById(ticketId);
    }

    public Ticket assignAgent(Ticket ticket, String agentId) {
        ticket.setAgentId(agentId);
        log.info("Agend id is assigned to ticket: "+ticket.getTicketId());
        return this.ticketRepository.save(ticket);
    }

    public List<Ticket> getAgentAssignedTickets() {
        List<Ticket> tickets = this.ticketRepository.findByAgentIdNotNull();
        log.info("Tickets fetched from db!");
        return tickets;
    }

    public List<Ticket> getAgentUnAssignedTickets() {
        List<Ticket> tickets = this.ticketRepository.findByAgentIdNull();
        log.info("Tickets fetched from db!");
        return tickets;
    }

    public List<Ticket> getTicketsByStatus(Status status) {
        List<Ticket> tickets = this.ticketRepository.findByStatus(status);
        log.info("Tickets fetched from db!");
        return tickets;
    }
}
