package org.example.daos;

import org.example.models.ProfileRequest;
import org.example.models.ProfileResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDao {

    public ProfileResponse getProfile(final String email) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT ProfilePicture FROM `User` WHERE Email=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new ProfileResponse(
                        resultSet.getString("ProfilePicture")
                );
            }
            return null;
        }
    }

    public void updateProfile(final ProfileRequest profileRequest)
        throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String updateStatement =
                    "UPDATE `User` SET ProfilePicture=? WHERE Email=?;";

            PreparedStatement statement =
                    connection.prepareStatement(updateStatement);

            statement.setString(1,
                    profileRequest.getProfilePicture());
            statement.setString(2, profileRequest.getEmail());

            statement.executeUpdate();
        }
    }
}
