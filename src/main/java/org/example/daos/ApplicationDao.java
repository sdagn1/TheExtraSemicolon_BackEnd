package org.example.daos;

import org.example.models.ApplicationRequest;
import org.example.models.ApplicationResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationDao {

    private final int s3LinkStatementIndex = 3;

    public int createApplication(final ApplicationRequest applicationRequest)
        throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String insertStatement = "INSERT INTO `Application` "
                    +
                    "(Email, RoleID, S3Link, Status) "
                    +
                    "VALUES (?,?,?,'In Progress');";
            PreparedStatement statement = connection
                    .prepareStatement(insertStatement);
            statement.setString(1, applicationRequest.getEmail());
            statement.setInt(2, applicationRequest.getRoleId());
            statement.setString(s3LinkStatementIndex,
                    applicationRequest.getS3Link());
            return statement.executeUpdate();
        }
    }
    public ApplicationResponse getApplicationById(final int roleId,
                                                  final String email)
    throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM `Application` WHERE Email=? "
                    +
                    "AND RoleID=?;";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setInt(2, roleId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new ApplicationResponse(
                        resultSet.getString("Email"),
                        resultSet.getInt("roleId")
                );
            }

            return null;
        }
    }

    public void deleteApplication(final int roleId,
                                  final String email)
    throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String deleteApp = "DELETE FROM `Application` "
                    +
                    "WHERE Email=? AND RoleID=?;";

            PreparedStatement deleteStatement = connection
                    .prepareStatement(deleteApp);
            deleteStatement.setString(1, email);
            deleteStatement.setInt(2, roleId);
            deleteStatement.executeUpdate();
        }
    }
}
