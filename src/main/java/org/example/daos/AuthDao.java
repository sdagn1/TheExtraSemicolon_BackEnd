package org.example.daos;

import org.example.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {

    public User getUser(final String email) throws
            SQLException {

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT Email, Salt FROM `User`"
                    +
                    " WHERE Email = ?;";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new User(
                        resultSet.getString("Email"),
                        resultSet.getString("Salt")
                );
            }
            return null;
        }
    }

    public User validateUser(final String email, final String password) throws
            SQLException {

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT Email, RoleID FROM `User`"
                    +
                    " WHERE Email = ? AND Password = ?;";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new User(
                        resultSet.getString("Email"),
                        resultSet.getInt("RoleID")
                );
            }
            return null;
        }
    }

}
