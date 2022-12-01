package com.example.whatstocleanbackend.service;

import com.example.whatstocleanbackend.Utils;
import com.example.whatstocleanbackend.domain.chore.Chore;
import com.example.whatstocleanbackend.domain.date.Date;
import com.example.whatstocleanbackend.exception.IndexException;
import com.example.whatstocleanbackend.repository.ChoreRepository;
import com.example.whatstocleanbackend.repository.UserIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChoreService {

    @Autowired
    private ChoreRepository repository;

    @Autowired
    private UserIndexRepository userIndexRepository;

    public List<Chore> getByDate(Integer year, Integer month, Integer day, Integer index) {
        Date date = Date.createDate(year, month, day);
        return getAll(index).stream()
                .filter(chore -> chore.getPeriodicity().matches(date))
                .collect(Collectors.toList());
    }

    public List<Chore> getAll(Integer index) {
        return repository.getAll(index).stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public void add(Chore chore, Integer index) {
        Boolean isRepeated = getAll(index).stream().anyMatch(choreItem -> Utils.sameChores(chore, choreItem));
        if (!isRepeated) {
            repository.add(chore, index);
        }
    }

    public void update(Chore chore, String roomName, String sectionName, String description, Integer index) {
        repository.update(chore, roomName, sectionName, description, index);
    }

    public void delete(String roomName, String sectionName, String description, Integer index) {
        repository.delete(roomName, sectionName, description, index);
    }

    public byte[] getFile(Integer index) {
        try {
            return Files.readAllBytes(repository.getPath(index));
        } catch (IOException e) {
            throw new IndexException();
        }
    }

    public void saveFile(Integer index, byte[] file) {
        repository.saveFile(index, file);
    }

    public void createFile(Integer index) {
        repository.createFile(index);
    }
}
