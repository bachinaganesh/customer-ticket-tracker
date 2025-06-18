package com.example.tickettracker.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.tickettracker.models.Role;
import com.example.tickettracker.models.User;
import com.example.tickettracker.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testCreatedUser() throws Exception{
        User inputUser = new User(null, "Ganesh", "ganesh@gmail.com", Role.CUSTOMER, LocalDateTime.now());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonBody = objectMapper.writeValueAsString(inputUser);
        when(userService.createUser(inputUser)).thenReturn(inputUser);

        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                            .content(jsonBody))
                                            .andExpect(status().isOk())
                                            .andExpect(jsonPath("$.name").value("Ganesh"))
                                            .andExpect(jsonPath("$.email").value("ganesh@gmail.com"));
        
        verify(userService, times(1)).createUser(inputUser);
                                        
    }
    
}
