package com.example.whatstocleanbackend.controller;

import com.example.whatstocleanbackend.aspects.annotation.LogDebug;
import com.example.whatstocleanbackend.domain.chore.Chore;
import com.example.whatstocleanbackend.service.ChoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*") // TODO
public class ChoreController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ChoreService service;

    private static Integer asInt(String index) {
        return Integer.valueOf(index);
    }

    @LogDebug
    @GetMapping("/chore/{year}-{month}-{day}")
    public List<Chore> getByDate(
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable Integer day,
            @RequestHeader(name = "user_index") String index
    ) {
        return service.getByDate(year, month, day, asInt(index));
    }

    @GetMapping("/chore")
    public List<Chore> getAll(@RequestHeader(name = "user_index") String index) {
        return service.getAll(asInt(index));
    }

    @PostMapping("/chore")
    public List<Chore> post(@RequestBody Chore chore, @RequestHeader(name = "user_index") String index) {
        service.add(chore, asInt(index));
        return service.getAll(asInt(index));
    }

    @PutMapping("/chore/{roomName}::{sectionName}::{description}")
    public List<Chore> put(
            @RequestBody Chore chore,
            @PathVariable String roomName,
            @PathVariable String sectionName,
            @PathVariable String description,
            @RequestHeader(name = "user_index") String index
    ) {
        service.update(chore, roomName, sectionName, description, asInt(index));
        return service.getAll(asInt(index));
    }

    @DeleteMapping("/chore/{roomName}::{sectionName}::{description}")
    public List<Chore> delete(
            @PathVariable String roomName,
            @PathVariable String sectionName,
            @PathVariable String description,
            @RequestHeader(name = "user_index") String index
    ) {
        service.delete(roomName, sectionName, description, asInt(index));
        return service.getAll(asInt(index));
    }

    @GetMapping("/file")
    public byte[] getFile(@RequestHeader(name = "user_index") String index) {
        return service.getFile(asInt(index));
    }

    @PostMapping("/file")
    public void postFile(@RequestHeader(name = "user_index") String index, @RequestParam MultipartFile file) {
        try {
            service.saveFile(asInt(index), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
