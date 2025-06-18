package com.example.tickettracker.exceptionhandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.tickettracker.exceptions.AssignedException;
import com.example.tickettracker.exceptions.InvalidValueException;
import com.example.tickettracker.exceptions.NotFoundException;
import com.example.tickettracker.responses.ResponseError;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class HandleExceptions {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        ResponseError error = new ResponseError(ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ResponseError> handleInvalidValueException(InvalidValueException ex) {
        ResponseError error = new ResponseError(ex.getMessage());
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(AssignedException.class)
    public ResponseEntity<ResponseError> handleAssignedException(AssignedException ex) {
        ResponseError error = new ResponseError(ex.getMessage());
        return ResponseEntity.ok(error);
    }
}
