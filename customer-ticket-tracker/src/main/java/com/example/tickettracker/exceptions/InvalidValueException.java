package com.example.tickettracker.exceptions;

public class InvalidValueException extends RuntimeException{
    
    public InvalidValueException(String message) {
        super(message);
    }
}
