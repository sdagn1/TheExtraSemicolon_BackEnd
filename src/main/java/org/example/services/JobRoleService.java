package org.example.services;

import org.example.daos.JobRoleDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.mappers.JobRoleMapper;
import org.example.models.JobRoleInfoResponse;
import org.example.models.JobRoleResponse;

import java.sql.SQLException;
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

    public JobRoleInfoResponse getJobRoleById(final int id)
            throws SQLException, DoesNotExistException {
        JobRoleInfoResponse jobRoleInfoResponse = JobRoleMapper
                .mapJobRoleToJobRoleInfo(
                jobRoleDao.getJobRoleById(id)
        );
        if (jobRoleInfoResponse == null) {
            throw new DoesNotExistException(Entity.JOBROLE);
        }
        return jobRoleInfoResponse;
    }

    public List<JobRoleResponse> getAllJobRoles()
            throws SQLException, DoesNotExistException {

        List<JobRoleResponse> jobRoleResponses =
                JobRoleMapper.mapJobRoleListToResponseList(
                        jobRoleDao.getAllJobRoles());

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
}
