package com.example.tickettracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.example.tickettracker.models.Role;
import com.example.tickettracker.models.User;
import com.example.tickettracker.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        log.info("creating user");
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String userId) {
        log.info("Fetching user with ID: {}", userId);
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers() {
        log.info("Users are fetching from database!");
        return userRepository.findAll();
    }

    public List<User> getAllCustomers(Role role) {
        log.info("customers fetching from db!");
        return this.userRepository.findByRole(role);
    }

    public List<User> getAllAgents(Role role) {
        log.info("agents fetching from db!");
        return this.userRepository.findByRole(role);
    }

    public List<User> getAllAdmins(Role role) {
        log.info("admins fetching from db!");
        return this.userRepository.findByRole(role);
    }

    public Optional<User> getAgentById(String agentId) {
        log.info("Fetching agent info from db!");
        return this.userRepository.findByRoleAndUserId(Role.AGENT, agentId);
    }

    public Optional<User> getAdminById(String admninId) {
        log.info("fetching admin from db!");
        return this.userRepository.findByRoleAndUserId(Role.ADMIN, admninId);
    }

    public Optional<User> getCustomerById(String customerId) {
        log.info("fetching customer from db!");
        return this.userRepository.findByRoleAndUserId(Role.CUSTOMER, customerId);
    }
}
