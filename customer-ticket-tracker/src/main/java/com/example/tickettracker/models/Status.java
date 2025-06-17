package com.example.tickettracker.models;

public enum Status {
    // mainly ticket has four status from ticket creation to ticket completion
    // only AGENT and ADMIn can change the status of the ticket
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED
}
