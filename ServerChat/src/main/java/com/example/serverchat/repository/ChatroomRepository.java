package com.example.serverchat.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatroomRepository {

    private static Connection connection;

    private static final Logger logger = LoggerFactory.getLogger(ChatroomRepository.class);

    public ChatroomRepository() {
        try {
            connection = DriverManager.getConnection(
                DatabaseConfig.url, 
                DatabaseConfig.user, 
                DatabaseConfig.pw
            );
        } catch (SQLException e) {
            logger.error("Error connecting to database", e);
        }
    }

    public void insertNewChatroom(final String chatroom) {
        try {
            final PreparedStatement statement = connection.prepareStatement(insertChatroomQuery);
            statement.setString(1, chatroom);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error inserting new chatroom", e);
        }
    }

    public List<String> getChatroomNames() {
        try {
            final PreparedStatement statement = connection.prepareStatement(getChatroomsQuery);
            final ResultSet results = statement.executeQuery();
            final List<String> chatrooms = new ArrayList<>();
            MessageRepository messageRepo = new MessageRepository();

            while(results.next()) {
                String chatroom = results.getString("room_name");
                chatrooms.add(results.getString("room_name")); 
            }
            return chatrooms;
        } catch (SQLException e) {
            logger.error("Error retrieving chatrooms", e);
            return new ArrayList<>();
        }
    }

    private static final String insertChatroomQuery = "INSERT INTO chatrooms(room_name) "
     + "VALUES(?)";

    private static final String getChatroomsQuery = "SELECT chatrooms.room_name FROM chatrooms";
}
