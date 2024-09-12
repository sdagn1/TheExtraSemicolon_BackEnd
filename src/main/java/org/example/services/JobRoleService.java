package org.example.services;

import org.example.daos.JobRoleDao;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidPageLimitException;
import org.example.mappers.JobRoleMapper;
import org.example.mappers.JobRoleToCSV;
import org.example.models.JobRole;
import org.example.models.JobRoleInfoResponse;
import org.example.models.JobRoleResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class JobRoleService {
    JobRoleDao jobRoleDao;
    public InputStream file;

    private String formatLocations(final List<String> locations) {
        return String.join(", ", locations);
    }

    public JobRoleService(final JobRoleDao jobRoleDao) {
        this.jobRoleDao = jobRoleDao;
    }

    public List<JobRoleResponse> getAllJobRoles(final int page, final int limit)
            throws SQLException, DoesNotExistException,
            InvalidPageLimitException {

        List<JobRoleResponse> jobRoleResponses = JobRoleMapper.
                mapJobRoleListToResponseList(
                jobRoleDao.getAllJobRoles(page, limit));

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

    public JobRoleInfoResponse getJobRoleById(final int id)
            throws SQLException, DoesNotExistException {
        JobRoleInfoResponse jobRoleInfoResponse = JobRoleMapper.
                mapJobRoleToJobRoleInfo(
                jobRoleDao.getJobRoleById(id));
        if (jobRoleInfoResponse == null) {
            throw new DoesNotExistException(Entity.JOBROLE);
        }
        return jobRoleInfoResponse;
    }

    public int getTotalJobRoles() throws SQLException {
        return jobRoleDao.getTotalJobRoles();
    }

    public InputStream getFullJobRoles()
    throws SQLException, DoesNotExistException, IOException {

        List<JobRole> jobRoles = jobRoleDao.getFullJobRoles();
        if (jobRoles.isEmpty()) {
            throw new DoesNotExistException(Entity.JOBROLERESPONSE);
        }
        List<JobRoleInfoResponse> jobRoleInfoResponse =
                JobRoleMapper.
                        mapJobRolesToJobRoleInfoList(
                                jobRoles);
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
        JobRoleToCSV.writeJobRoleForPipeSeparatorCSV(
                jobRoleInfoResponse, byteArrayOutputStream);
        file = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return file;
    }
}
