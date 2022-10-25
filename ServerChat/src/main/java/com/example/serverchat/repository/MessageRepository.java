package com.example.serverchat.repository;

import com.example.serverchat.dao.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {

    private static Connection connection;

    private static final Logger logger = LoggerFactory.getLogger(MessageRepository.class);

    public MessageRepository() {
        try {
            connection = DriverManager.getConnection(
                DatabaseConfig.url, 
                DatabaseConfig.user, 
                DatabaseConfig.pw
            );
        } catch (SQLException e) {
            logger.error("Unable to connect to db", e);
        }
    }

    public void insertMessageIntoDb(final String message,
        final String userId, final String chatroom) {
        try {
            logger.info("Posting message: {} by {}", message, userId);
            final PreparedStatement statement = connection.prepareStatement(insertMessageQuery);
            statement.setString(1, message);
            statement.setString(2, userId);
            statement.setString(3, chatroom);
            statement.setTimestamp(4, Timestamp.from(Instant.now()));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Unable to insert message into db", e);
        }
    }

    public List<Message> getXMostRecentMessagesFromChatroom(final int x, final String chatroom) {
        try {
            final PreparedStatement statement = connection.prepareStatement(getRecentMessagesQuery);
            statement.setString(1, chatroom);
            statement.setInt(2, x);
            final ResultSet results = statement.executeQuery();
            final ArrayList<Message> messages = new ArrayList<>();

            while(results.next()) {
                messages.add(new Message(results.getString("message_content"),
                    results.getString("username"), results.getTimestamp("message_timestamp")));
                logger.info("Getting message {} by {} at {}", results.getString("message_content"),
                    results.getString("username"), results.getTimestamp("message_timestamp"));
            }
            return messages;
        } catch (SQLException e) {
            logger.error("Unable to retrieve messages from db", e);
            return new ArrayList<>();
        }
    }

    private static final String insertMessageQuery = "INSERT INTO messages(message_content, "
        + "username, room_name, message_timestamp) VALUES(?, ?, ?, ?)";

    private static final String getRecentMessagesQuery = "SELECT message_content, username, message_timestamp FROM "
        + "messages WHERE room_name = ? ORDER BY message_timestamp DESC LIMIT ?";
}
