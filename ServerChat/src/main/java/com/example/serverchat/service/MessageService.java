package com.example.serverchat.service;

import com.example.serverchat.dao.Message;
import com.example.serverchat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    /**
     * Posts a new message
     * @param message the message to post
     * @param chatroom the chatroom to post to
     */
    public void postMessage(final Message message, final String chatroom) {
        repository.insertMessageIntoDb(message.getMessageContent(), message.getUsername(), chatroom);
    }

    /**
     * Gets a list of messages for the chatroom
     * @param chatroom the chatroom to get messages for
     * @return a list of messages
     */
    public List<Message> getMessages(final String chatroom) {
        return repository.getXMostRecentMessagesFromChatroom(50, chatroom);
    }
}
