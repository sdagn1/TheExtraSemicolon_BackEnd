package org.example.services;

import org.example.daos.JobRoleDao;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.models.JobRole;

import java.sql.SQLException;
import java.util.List;

public class JobRoleService {
    JobRoleDao jobRoleDao;

    public JobRoleService(final JobRoleDao jobRoleDao) {
        this.jobRoleDao = jobRoleDao;
    }
    public List<JobRole> getAllJobRolesFull() throws SQLException {
        return jobRoleDao.getAllJobRolesFull();
    }

    public JobRole getJobRoleById(final int id)
            throws SQLException, DoesNotExistException {
        JobRole jobRole = jobRoleDao.getJobRoleById(id);

        if (jobRole == null) {
            throw new DoesNotExistException(Entity.JOBROLE);
        }
        return jobRole;
    }
}
