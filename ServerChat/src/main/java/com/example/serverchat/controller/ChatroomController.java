package com.example.serverchat.controller;

import com.example.serverchat.service.ChatroomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/chatrooms")
@RestController
public class ChatroomController {
   
    @Autowired
    private ChatroomService service;

    private static final Logger logger = LoggerFactory.getLogger(ChatroomController.class);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity getAllChatrooms() {
        final List<String> chatrooms = service.getAllChatroomsInOrderOfActivity();
        logger.info("Returning chatrooms {}", chatrooms);
        return ResponseEntity.ok(chatrooms);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void createNewChattoom(@RequestBody final String chatroom) {
        service.createNewChattoom(chatroom);
    }
}
