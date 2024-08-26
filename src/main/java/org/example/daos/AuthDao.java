package org.example.daos;

import org.example.models.LoginRequest;
import org.example.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {

    public User getUser(final LoginRequest loginRequest) throws
            SQLException {

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT Email, Password, Salt, RoleID FROM `User`"
                    +
                    " WHERE Email = ?;";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, loginRequest.getEmail());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new User(
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getString("Salt"),
                        resultSet.getInt("RoleID")
                );
            }
            return null;
        }
    }

    public User validateUser(final LoginRequest loginRequest) throws
            SQLException {

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT Email, Password, Salt, RoleID FROM `User`"
                    +
                    " WHERE Email = ? AND Password = ?;";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, loginRequest.getEmail());
            statement.setString(2, loginRequest.getPassword());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new User(
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getString("Salt"),
                        resultSet.getInt("RoleID")
                );
            }
            return null;
        }
    }

}