package com.example.serverchat.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {

    private static Connection connection;

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository() {
        try {
            connection = DriverManager.getConnection(
                DatabaseConfig.url, 
                DatabaseConfig.user, 
                DatabaseConfig.pw
            );
        } catch (SQLException e) {
            logger.error("Error connecting to db", e);
        }
    }

    public boolean checkUserExistence(final String userName) {
        try {
            logger.info("Search db for user {}", userName);
            final PreparedStatement statement = connection.prepareStatement(searchUserQuery);
            statement.setString(1, userName);
            final ResultSet results = statement.executeQuery();
            logger.info("Checking results: {}", results.toString());
            return results.next();
        } catch (SQLException e) {
            logger.error("Unable to check for user {}, {}", userName, e);
            return false;
        }
    }

    public void createUser(final String userName) {
        try {
            final PreparedStatement statement = connection.prepareStatement(createUserQuery);
            statement.setString(1, userName);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Unable to create user {}, {}", userName, e);
        }
    }

    private static final String createUserQuery = "INSERT INTO users(username) "
        + "VALUES(?)";

    private static final String searchUserQuery = "SELECT user_id FROM users WHERE "
        + "username = ?";
}
