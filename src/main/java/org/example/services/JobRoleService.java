package org.example.services;

import org.example.daos.JobRoleDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidPageLimitException;
import org.example.mappers.JobRoleMapper;
import org.example.models.JobRoleResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JobRoleService {
    JobRoleDao jobRoleDao;
    DatabaseConnector databaseConnector;
    private String formatLocations(final List<String> locations) {
        return String.join(", ", locations);
    }

    public JobRoleService(final JobRoleDao jobRoleDao) {
        this.jobRoleDao = jobRoleDao;
    }

//    public List<JobRoleResponse> getAllJobRoles()
//            throws SQLException, DoesNotExistException {
//
//        List<JobRoleResponse> jobRoleResponses =
//                JobRoleMapper.
//                        mapJobRoleListToResponseList(
//                                jobRoleDao.getAllJobRoles());
//
//        if (jobRoleResponses.isEmpty()) {
//            throw new DoesNotExistException(Entity.JOBROLERESPONSE);
//        }
//
//        jobRoleResponses.forEach(response -> {
//            String formattedLocations = formatLocations(
//                    response.getLocations());
//            response.setFormattedLocations(formattedLocations);
//        });
//
//        return jobRoleResponses;
//    }


    public List<JobRoleResponse> getAllJobRoles(final int page,
                                                      final int limit)
            throws SQLException, DoesNotExistException,
            InvalidPageLimitException {

        List<JobRoleResponse> jobRoleResponses =
                JobRoleMapper.
                        mapJobRoleListToResponseList(
                                jobRoleDao.getPaginatedJobRoles(page, limit));

        if (jobRoleResponses.isEmpty()) {
            throw new DoesNotExistException(Entity.JOBROLERESPONSE);
        }

        jobRoleResponses.forEach(response -> {
            String formattedLocations = formatLocations(
                    response.getLocations());
            response.setFormattedLocations(formattedLocations);
        });
        return jobRoleResponses;
    }


    public int getTotalJobRoles() throws SQLException {
        return jobRoleDao.getTotalJobRoles();
    }


}
