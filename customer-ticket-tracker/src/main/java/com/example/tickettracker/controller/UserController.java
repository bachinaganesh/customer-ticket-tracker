package com.example.tickettracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tickettracker.exceptions.NotFoundException;
import com.example.tickettracker.models.Role;
import com.example.tickettracker.models.User;
import com.example.tickettracker.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // creating an user
        User createdUser = userService.createUser(user);
        log.info("User created successfully:");
        return ResponseEntity.ok(createdUser);
    }
    
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        // fetch the user based on userId
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        log.info("User fetched successfully with ID: {}", userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        // get all the avilable list of users
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()) {
            log.warn("No users found!");
        }
        else {
            log.info("users fetched successfully!");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/customers")
    public ResponseEntity<List<User>> getAllCustomers() {
        // get the users for ROLE as "CUSTOMER"
        List<User> users = userService.getAllCustomers(Role.CUSTOMER);
        if(users.isEmpty()) {
            log.info("No customers found!");
        }
        else {
            log.info("Customers fetched from database!");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/agents")
    public ResponseEntity<List<User>> getAllAgents() {
        // get the users for ROLE as "AGENT"
        List<User> users = this.userService.getAllAgents(Role.AGENT);
        if(users.isEmpty()) {
            log.info("No agents found!");
        }
        else {
            log.info("Agents fetched from database!");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        // get the users for ROLE as "ADMIN"
        List<User> users =  this.userService.getAllAdmins(Role.ADMIN);
        if(users.isEmpty()) {
            log.info("admins not found!");
        }
        else {
            log.info("admins fetched from database!");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/agents/{agentId}")
    public ResponseEntity<User> getAgentById(@PathVariable String agentId) {
        // get user only agent role with userid
        User user = this.userService.getAgentById(agentId).orElseThrow(()-> new NotFoundException("No user found with Role 'AGENT' and id: "+agentId));
        log.info("Fetched agent information with id: "+agentId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/admins/{adminId}")
    public ResponseEntity<User> getAdminById(@PathVariable String adminId) {
        // get user only admin role with userid
        User user = this.userService.getAgentById(adminId).orElseThrow(()-> new NotFoundException("No user found with Role 'ADMIN' and id: "+adminId));
        log.info("Fetched agent information with id: "+adminId);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/users/customers/{customerId}")
    public ResponseEntity<User> getCustomerById(@PathVariable String customerId) {
        // get user only customer role with userid
        User user = this.userService.getCustomerById(customerId).orElseThrow(()-> new NotFoundException("No user found with Role 'CUSTOMER' and id: "+customerId));
        log.info("Fetched agent information with id: "+customerId);
        return ResponseEntity.ok(user);
    }


}