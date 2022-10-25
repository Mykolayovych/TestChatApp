package com.example.serverchat.controller;

import com.example.serverchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth")
    public boolean authenticate(@RequestBody final String username) {
        return service.authenticateUser(username);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void create(@RequestBody final String username) {
        service.createNewUser(username);
    }
}