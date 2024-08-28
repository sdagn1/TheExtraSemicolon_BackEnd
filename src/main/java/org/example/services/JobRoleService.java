package org.example.services;

import org.example.daos.JobRoleDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.mappers.JobRoleMapper;
import org.example.models.JobRoleResponse;
import org.example.validators.JobRoleValidator;

import java.sql.SQLException;
import java.util.List;

public class JobRoleService {
    JobRoleDao jobRoleDao;
    JobRoleValidator jobRoleValidator;
    DatabaseConnector databaseConnector;

    public JobRoleService(final JobRoleDao jobRoleDao,
                          final JobRoleValidator jobRoleValidator) {
        this.jobRoleDao = jobRoleDao;
        this.jobRoleValidator = jobRoleValidator;
    }

    public List<JobRoleResponse> getAllJobRoles()
            throws SQLException, DoesNotExistException {
        List<JobRoleResponse> jobRoleResponses =
                JobRoleMapper.
                        mapJobRoleListToResponseList(
                                jobRoleDao.getAllJobRoles());

        if (jobRoleResponses.isEmpty()) {
            throw new DoesNotExistException(Entity.JOBROLERESPONSE);
        }

        return jobRoleResponses;
    }
}
