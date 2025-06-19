package com.example.tickettracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tickettracker.exceptions.AssignedException;
import com.example.tickettracker.exceptions.InvalidValueException;
import com.example.tickettracker.exceptions.NotFoundException;
import com.example.tickettracker.models.Role;
import com.example.tickettracker.models.Status;
import com.example.tickettracker.models.Ticket;
import com.example.tickettracker.service.TicketService;
import com.example.tickettracker.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api")
@Slf4j
public class TicketController {
    
    private TicketService ticketService;
    private UserService userService;

    public TicketController(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }


    @PostMapping("/tickets")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {

        // Raise exception if customer id is null
        if(ticket.getCustomerId() == null) {
            throw new InvalidValueException("Customer id must be required while creating an ticket!");
        } 

        // If provided customer id value provided and customer not avaiable in database raise an error
        this.userService.getCustomerById(ticket.getCustomerId()).orElseThrow(() -> new NotFoundException("User not found with an id "+ticket.getCustomerId()+" and role as "+Role.CUSTOMER));

        // create a ticket
        Ticket createdTicket = this.ticketService.createTicket(ticket);
        log.info("Ticket is created successfully with id: "+createdTicket.getTicketId());
        return ResponseEntity.ok(createdTicket);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        // fetch all the avaiable tickets from database
        List<Ticket> tickets = this.ticketService.getAllTickets();
        if(tickets.isEmpty()) {
            log.info("No ticktes found in db!");
        }
        else {
            log.info("Tickets successfully fetched from db!");
        }
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String ticketId) {
        Ticket ticket = this.ticketService.getTicketById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found with an id: "+ticketId));
        log.info("Ticket info fetched successfully with an id: "+ticketId);
        return ResponseEntity.ok(ticket);
    }
    
    @PatchMapping("/tickets/assign")
    public ResponseEntity<Ticket> assignTicket(@RequestParam String ticketId, @RequestParam String agentId) {
        // This is responsible for assigning a ticket

        // check ticket existed in db or not
        Ticket ticket = this.ticketService.getTicketById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found with an id: "+ticketId));

        // Check agent already assigned or not. If assigned then throw an exception alreay agent assigned
        if(ticket.getAgentId() != null) {
            throw new AssignedException("Agent is already assigned for the ticked id: "+ticketId);
        }

        // check agent existed in db or not
        this.userService.getAgentById(agentId).orElseThrow(() -> new NotFoundException("User not found with an id: "+agentId+" and role as "+Role.AGENT));

        // assign agent to the ticket
        Ticket updatedTicket = this.ticketService.assignAgent(ticket, agentId);
        log.info("Ticket is updated into db and assigned agent successfully!");
        return ResponseEntity.ok(updatedTicket);

    }

    @GetMapping("/admins/tickets/assign")
    public ResponseEntity<List<Ticket>> getAllAgentAssignedTickets(@RequestParam String adminId) {
        // get the all the tickets which is assigned by agent 
        // tickets can get only the use has "ADMIN Role"

        this.userService.getAdminById(adminId).orElseThrow(()-> new NotFoundException("User not found with role 'ADMIN' and id: "+adminId));

        log.info("Admin "+adminId+" finding getting all agent assigned tickets");
        List<Ticket> tickets = this.ticketService.getAgentAssignedTickets();

        if(tickets.isEmpty()) {
            log.info("No tickets found which is assigned by agents!");
        }
        else {
            log.info("Tickets found which is assigned by agent");
        }
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/admins/tickets/unassign")
    public ResponseEntity<List<Ticket>> getAllAgentUnAssignedTickets(@RequestParam String adminId) {
        // get the all the tickets which is not assigned by agent 
        // tickets can get only the use has "ADMIN Role"

        this.userService.getAdminById(adminId).orElseThrow(()-> new NotFoundException("User not found with role 'ADMIN' and id: "+adminId));

        log.info("Admin "+adminId+" finding getting all agent not assigned tickets");
        List<Ticket> tickets = this.ticketService.getAgentUnAssignedTickets();

        if(tickets.isEmpty()) {
            log.info("No tickets found which is not assigned by agents!");
        }
        else {
            log.info("Tickets found which is not assigned by agent");
        }
        return ResponseEntity.ok(tickets);
    }
    
    @GetMapping("/admins/tickets/status")
    public ResponseEntity<List<Ticket>> getTicketsByStatus(@RequestParam String adminId, @RequestParam Status status) {
        // get the tickets which is respective to the status that is sent
        // only admins can get the tickets 

        this.userService.getAdminById(adminId).orElseThrow(()-> new NotFoundException("User not found with role 'ADMIN' and id: "+adminId));
        
        List<Ticket> tickets = this.ticketService.getTicketsByStatus(status);
        if(tickets.isEmpty()) {
            log.info("No tickets found with status "+status);
        }
        else {
            log.info("Tickets found with status "+status);
        }
        return ResponseEntity.ok(tickets);
    }
    
    
}
