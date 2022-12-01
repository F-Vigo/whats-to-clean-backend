package com.example.whatstocleanbackend.controller;

import com.example.whatstocleanbackend.aspects.annotation.LogInfo;
import com.example.whatstocleanbackend.service.UserIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*") // TODO
public class UserIndexController {

    @Autowired
    private UserIndexService service;

    @LogInfo
    @GetMapping("/login")
    public int logIn() {
        return service.logIn();
    }

    @LogInfo
    @DeleteMapping("/logout")
    public void logOut(@RequestHeader(name = "user_index") String index) {
        log.info(String.format("Logging out user with index %s.", index));
        service.logOut(Integer.parseInt(index));
    }

}
