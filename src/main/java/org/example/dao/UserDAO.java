package org.example.dao;

import org.example.model.User;
import org.example.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User getUserByUsername(String username) throws SQLException {
        Connection connection = Database.getConnection();

        String query = "SELECT * FROM users WHERE username = ?";

        PreparedStatement stmt = connection.prepareStatement(query);

        stmt.setString(1, username);

        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            User user = new User();

            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));

            return user;
        }

        return null;
    }

    public void createUser(User user) throws SQLException {
        Connection connection = Database.getConnection();

        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        PreparedStatement stmt = connection.prepareStatement(query);

        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());

        stmt.executeUpdate();
    }

}
