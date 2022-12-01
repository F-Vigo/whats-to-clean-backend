package com.example.whatstocleanbackend.controller;

import com.example.whatstocleanbackend.DisplayNameGeneratorCustom;
import com.example.whatstocleanbackend.exception.DateException;
import com.example.whatstocleanbackend.service.ChoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.example.whatstocleanbackend.TestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayNameGeneration(DisplayNameGeneratorCustom.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChoreControllerTest {

    @MockBean
    private ChoreService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetByDate_withValidDate_thenOk() throws Exception {
        when(service.getByDate(2020, 01, 01, INDEX)).thenReturn(List.of(CHORE));
        mockMvc.perform(MockMvcRequestBuilders.get("/chore/2020-01-01").header("user_index", INDEX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
        verify(service, times(1)).getByDate(2020, 01, 01, INDEX);
    }

    @Test
    public void whenGetByDate_withInvalidDate_then400() throws Exception {
        when(service.getByDate(2020,01,00, INDEX)).thenThrow(DateException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/chore/2020-01-00").header("user_index", INDEX))
                .andExpect(status().isBadRequest());
        verify(service, times(1)).getByDate(2020, 01, 00, INDEX);
    }

    @Test
    public void whenGetAll_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chore").header("user_index", INDEX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).getAll(INDEX);
    }

    @Test
    public void whenPost_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/chore", CHORE)
                        .header("user_index", INDEX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CHORE))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).add(CHORE, INDEX);
    }

    @Test
    public void whenPut_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/chore/room::section::description")
                        .header("user_index", INDEX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CHORE))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).update(CHORE, ROOM.getName(), SECTION.getName(), DESCRIPTION, INDEX);
    }

    @Test
    public void whenDelete_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/chore/room::section::description").header("user_index", INDEX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).delete(ROOM.getName(), SECTION.getName(), DESCRIPTION, INDEX);
    }

    @Test
    public void whenGetFile_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/file").header("user_index", INDEX))
                .andExpect(status().isOk());
        verify(service, times(1)).getFile(INDEX);
    }
}
