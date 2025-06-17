package com.example.tickettracker.exceptionhandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
