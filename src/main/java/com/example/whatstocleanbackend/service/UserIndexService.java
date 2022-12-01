package com.example.whatstocleanbackend.service;

import com.example.whatstocleanbackend.domain.UserIndex;
import com.example.whatstocleanbackend.repository.UserIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class UserIndexService {

    @Autowired
    private UserIndexRepository repository;

    @Autowired
    private ChoreService choreService;

    public int logIn() {
        repository.save(new UserIndex());
        Integer index = getLastIndex();
        choreService.createFile(index);
        return index;
    }

    public void logOut(int index) {
        repository.deleteById(index);
    }
    private int getLastIndex() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(UserIndex::getIndex)
                .reduce((Integer x, Integer y) -> (x < y ? y : x))
                .get();
    }

}
