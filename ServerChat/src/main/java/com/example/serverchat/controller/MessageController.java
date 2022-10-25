package com.example.serverchat.controller;

import com.example.serverchat.dao.Message;
import com.example.serverchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/messages")
@RestController
public class MessageController {
    
    @Autowired
    private MessageService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{chatroom}")
    public List<Message> getMessagesForChatroom(@PathVariable final String chatroom) {
        return service.getMessages(chatroom);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{chatroom}")
    public void postMessage(@PathVariable final String chatroom, @RequestBody final Message message) {
        service.postMessage(message, chatroom);
    }
}
