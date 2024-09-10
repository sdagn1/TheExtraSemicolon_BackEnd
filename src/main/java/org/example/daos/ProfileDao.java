package org.example.daos;

import org.example.models.ProfileRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileDao {

    public void updateProfile(final ProfileRequest profileRequest)
        throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String updateStatement =
                    "UPDATE `USER` SET ProfilePicture=? WHERE Email=?;";

            PreparedStatement statement =
                    connection.prepareStatement(updateStatement);

            statement.setString(1,
                    profileRequest.getProfilePicture());
            statement.setString(2, profileRequest.getEmail());

            statement.executeUpdate();
        }
    }
}
