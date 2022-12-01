package com.example.whatstocleanbackend.controller;

import com.example.whatstocleanbackend.DisplayNameGeneratorCustom;
import com.example.whatstocleanbackend.service.UserIndexService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserIndexControllerTest {

    private static final Integer INDEX = 1;

    @MockBean
    private UserIndexService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void logIn() throws Exception {
        when(service.logIn()).thenReturn(INDEX);
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(INDEX)));
        verify(service, times(1)).logIn();
    }

    @Test
    public void logOut_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/logout").header("user_index", INDEX))
                .andExpect(status().isOk());
        verify(service, times(1)).logOut(INDEX);
    }

    @Test
    public void logOut_Error() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/logout"))
                .andExpect(status().is4xxClientError());
        verify(service, never()).logOut(INDEX);
    }
}
