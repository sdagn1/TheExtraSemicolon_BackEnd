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
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    public List<JobRole> getAllJobRoles(final int page, final int limit)
            throws SQLException, DoesNotExistException,
            InvalidPageLimitException {
        List<JobRole> jobRoles = new ArrayList<>();
        JobRoleValidator jobRoleValidator = new JobRoleValidator();
        jobRoleValidator.validateJobRolePagination(page, limit);

        int offset = (page - 1) * limit;
        String query = "SELECT \n"
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
                + "    locations\n"
                + "FROM \n"
                + "    Job_Roles jr\n"
                + "JOIN \n"
                + "    Job_Bands jb ON jr.band = jb.jobBandsId\n"
                + "JOIN \n"
                + "    (SELECT roleId, GROUP_CONCAT("
                + "locationName SEPARATOR ', ') AS locations\n"
                + "     FROM Job_Location_Connector jlc\n"
                + "     JOIN Job_Locations jl ON"
                + " jlc.roleLocationId = jl.roleLocationId\n"
                + "     GROUP BY roleId) loc ON jr.roleId = loc.roleId\n"
                + "WHERE \n"
                + "    jr.status = 1 \n"
                + "    AND jr.positionsAvailable > 0 \n"
                + "LIMIT ? OFFSET ?;";


        try (Connection connection = DatabaseConnector.getConnection()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            ResultSet resultSet = statement.executeQuery();
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
        String query = "SELECT COUNT(*) AS total FROM Job_Roles "
                + "WHERE status = 1 "
                + "AND positionsAvailable > 0;";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
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

    public List<JobRole> getFullJobRoles()
    throws SQLException, DoesNotExistException,
            InvalidPageLimitException {
        List<JobRole> jobRoles = new ArrayList<>();
        String query = "SELECT \n"
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
                + "    COALESCE(locations, '') AS locations\n"
                + "FROM \n"
                + "    Job_Roles jr\n"
                + "JOIN \n"
                + "    Job_Bands jb ON jr.band = jb.jobBandsId\n"
                + "JOIN \n"
                + "    (SELECT roleId, GROUP_CONCAT("
                + "locationName SEPARATOR ', ') AS locations\n"
                + "     FROM Job_Location_Connector jlc\n"
                + "     JOIN Job_Locations jl ON"
                + " jlc.roleLocationId = jl.roleLocationId\n"
                + "     GROUP BY roleId) loc ON jr.roleId = loc.roleId;";



        try (Connection connection = DatabaseConnector.getConnection()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
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

                String locationsString = resultSet.getString("locations");
                if (locationsString != null) {
                    String[] locationStrings = locationsString.split(", ");
                    List<Location> locations = new ArrayList<>();
                    for (String locationString : locationStrings) {
                        locations.add(Location.fromString(locationString));
                    }
                    jobRole.setLocations(locations);
                } else {
                    jobRole.setLocations(new ArrayList<>());
                }


                jobRoles.add(jobRole);
            }
            return jobRoles;
        }
    }
}
