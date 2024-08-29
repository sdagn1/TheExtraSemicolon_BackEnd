package org.example.daos;

import org.example.enums.Capability;
import org.example.enums.JobBands;
import org.example.enums.Locations;
import org.example.models.JobRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    public List<JobRole> getAllJobRoles() throws SQLException {
        List<JobRole> jobRoles = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection()) {
//            assert connection != null;
            Statement statement = connection.createStatement();
            System.out.println("HERE");

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
                            + "GROUP BY \n"
                            + "    jr.roleId, jr.roleName, jr.description,"
                            + "jr.responsibilities, jr.linkToJobSpec,"
                            + "jr.capability, jb.jobBandsEnum, jr.closingDate,"
                            + "jr.status, jr.positionsAvailable;"
            );

            while (resultSet.next()) {
                JobRole jobRole = new JobRole(
                        resultSet.getInt("roleId"),
                        resultSet.getString("roleName"),
                        resultSet.getString("description"),
                        resultSet.getString("responsibilities"),
                        resultSet.getString("linkToJobSpec"),
                        JobBands.fromString(resultSet.getString("band")),
                        resultSet.getTimestamp("closingDate")
                );
                jobRole.setCapability(Capability.fromString(
                        resultSet.getString("capability")));
                jobRole.setStatus(resultSet.getBoolean("status"));
                jobRole.setPositionsAvailable(resultSet.getInt(
                        "positionsAvailable"));

                String[] locationStrings = resultSet.getString("locations").split(", ");
                List<Locations> locations = new ArrayList<>();
                for (String locationString : locationStrings) {
                    locations.add(Locations.fromString(locationString));
                }
                jobRole.setLocations(locations);


                jobRoles.add(jobRole);
            }
            return jobRoles;
        }
    }
}
