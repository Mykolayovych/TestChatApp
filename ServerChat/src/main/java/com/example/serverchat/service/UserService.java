package com.example.serverchat.service;

import com.example.serverchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository respository;

    /**
     * Authenticates a user
     * @param username the username to authenticate
     * @return true if authenticated, false otherwise
     */
    public boolean authenticateUser(final String username) {
        return respository.checkUserExistence(username);
    }

    /**
     * Creates a new user
     * @param username the username to create
     */
    public void createNewUser(final String username) {
        respository.createUser(username);
    }
}
