package org.example.daos;

import org.example.enums.Capability;
import org.example.enums.JobBand;
import org.example.enums.Location;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidPageLimitException;
import org.example.models.JobRole;
import org.example.validators.JobRoleValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    public List<JobRole> getAllJobRoles(final int page, final int limit)
            throws SQLException, DoesNotExistException,
            InvalidPageLimitException {
        List<JobRole> jobRoles = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection()) {
            assert connection != null;
            Statement statement = connection.createStatement();

            JobRoleValidator jobRoleValidator = new JobRoleValidator();
            jobRoleValidator.validateJobRolePagination(page, limit);

            int offset = (page - 1) * limit;

            ResultSet resultSet = statement.executeQuery(
                    "SELECT \n"
                   + "    jr.roleId,\n"
                   + "    jr.roleName,\n"
                   + "    jr.description,\n"
                   + "    jr.responsibilities,\n"
                   + "    jr.linkToJobSpec,\n"
                   + "    jr.capability,\n"
                   + "    jb.jobBandsEnum AS band,\n"
                   + "    jr.closingDate,\n"
                   + "    jr.status,\n"
                   + "    jr.positionsAvailable,\n"
                   + "    GROUP_CONCAT(jl.locationName SEPARATOR ', ')"
                   + " AS locations\n"
                   + "FROM \n"
                   + "    Job_Roles jr\n"
                   + "JOIN \n"
                   + "    Job_Bands jb ON jr.band = jb.jobBandsId\n"
                   + "JOIN \n"
                   + "    Job_Location_Connector jlc"
                   + " ON jr.roleId = jlc.roleId\n"
                   + "JOIN \n"
                   + " Job_Locations jl "
                   + "ON jlc.roleLocationId = jl.roleLocationId\n"
                   + "WHERE \n"
                   + "    jr.status = 1 \n"
                   + "    AND jr.positionsAvailable > 0 \n"
                   + "GROUP BY \n"
                   + "    jr.roleId, \n"
                   + "    jr.roleName, \n"
                   + "    jr.description,\n"
                   + "    jr.responsibilities, \n"
                   + "    jr.linkToJobSpec,\n"
                   + "    jr.capability, \n"
                   + "    jb.jobBandsEnum, \n"
                   + "    jr.closingDate,\n"
                   + "    jr.status, \n"
                   + "    jr.positionsAvailable \n"
                   + "LIMIT " + limit + " OFFSET " + offset + ";"
            );
            System.out.println(resultSet);

            if (!resultSet.isBeforeFirst()) {
                throw new DoesNotExistException(Entity.JOBROLERESPONSE);
            }

            while (resultSet.next()) {
                if (resultSet.wasNull() && resultSet.next()) {
                    resultSet.next();
                }
                JobRole jobRole = new JobRole(
                        resultSet.getInt("roleId"),
                        resultSet.getString("roleName"),
                        resultSet.getString("description"),
                        resultSet.getString("responsibilities"),
                        resultSet.getString("linkToJobSpec"),
                        JobBand.fromString(
                                resultSet.getString("band")),
                        resultSet.getTimestamp("closingDate")
                );
                jobRole.setCapability(Capability.fromString(
                        resultSet.getString("capability")));
                jobRole.setStatus(resultSet.getBoolean("status"));
                jobRole.setPositionsAvailable(resultSet.getInt(
                        "positionsAvailable"));

                String[] locationStrings = resultSet.
                        getString("locations").split(", ");
                List<Location> locations = new ArrayList<>();
                for (String locationString : locationStrings) {
                    locations.add(Location.fromString(locationString));
                }
                jobRole.setLocations(locations);


                jobRoles.add(jobRole);
            }
            return jobRoles;
        }
    }

    public int getTotalJobRoles() throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT COUNT(*) AS total FROM Job_Roles "
                             + "WHERE status = 1 "
                             + "AND positionsAvailable > 0;")) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        }
        return 0;
    }
    public JobRole getJobRoleById(final int id) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query =
                    "SELECT \n"
                            + "    jr.roleId,\n"
                            + "    jr.roleName,\n"
                            + "    jr.description,\n"
                            + "    jr.responsibilities,\n"
                            + "    jr.linkToJobSpec,\n"
                            + "    jr.capability,\n"
                            + "    jb.jobBandsEnum AS band,\n"
                            + "    jr.closingDate,\n"
                            + "    jr.status,\n"
                            + "    jr.positionsAvailable,\n"
                            + "    GROUP_CONCAT(jl.locationName SEPARATOR ', ')"
                            + "AS locations\n"
                            + "FROM \n"
                            + "    Job_Roles jr\n"
                            + "JOIN \n"
                            + "    Job_Bands jb ON jr.band = jb.jobBandsId\n"
                            + "JOIN \n"
                            + "    Job_Location_Connector jlc ON jr.roleId"
                            + "= jlc.roleId\n"
                            + "JOIN \n"
                            + "    Job_Locations jl ON jlc.roleLocationId"
                            + "= jl.roleLocationId\n"
                            + "WHERE jr.roleId = ?\n"
                            + "GROUP BY \n"
                            + "    jr.roleId, jr.roleName, jr.description,"
                            + "jr.responsibilities, jr.linkToJobSpec,"
                            + "jr.capability, jb.jobBandsEnum, jr.closingDate,"
                            + "jr.status, jr.positionsAvailable;";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                JobRole jobRole = new JobRole(
                        resultSet.getInt("roleId"),
                        resultSet.getString("roleName"),
                        resultSet.getString("description"),
                        resultSet.getString("responsibilities"),
                        resultSet.getString("linkToJobSpec"),
                        JobBand.fromString(
                                resultSet.getString("band")),
                        resultSet.getTimestamp("closingDate")
                );
                jobRole.setCapability(Capability.fromString(
                        resultSet.getString("capability")));
                jobRole.setStatus(resultSet.getBoolean("status"));
                jobRole.setPositionsAvailable(resultSet.getInt(
                        "positionsAvailable"));
                String[] locationStrings = resultSet.
                        getString("locations").split(", ");
                List<Location> locations = new ArrayList<>();
                for (String locationString : locationStrings) {
                    locations.add(Location.fromString(locationString));
                }
                jobRole.setLocations(locations);
                return jobRole;
            }
        }
        return null;
    }
}
