package org.example.validators;

import org.example.daos.JobRoleDao;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidPageLimitException;
import org.example.services.JobRoleService;

import java.sql.SQLException;

public class JobRoleValidator {


    public void validateJobRolePagination(final int page, final int limit)
            throws InvalidPageLimitException, SQLException {
        JobRoleDao jobRoleDao = new JobRoleDao();
        JobRoleService jobRoleService = new JobRoleService(jobRoleDao);
        final int limit1 = 10;
        final int limit2 = 25;
        final int limit3 = 50;
        final int limit4 = 100;
        int[] allowedLimits = {limit1, limit2, limit3, limit4};
        boolean isValidLimit = false;
        for (int allowedLimit : allowedLimits) {
            if (limit == allowedLimit) {
                isValidLimit = true;
                break;
            }
        }

        if (!isValidLimit) {
            throw new InvalidPageLimitException(
                    Entity.JOBROLERESPONSE, "Invalid page limit number");
        }

        int totalJobRoles = jobRoleDao.getTotalJobRoles();
        if (page > (int) Math.ceil((double) totalJobRoles / limit)) {
            throw new InvalidPageLimitException(
                    Entity.JOBROLERESPONSE, "Invalid page selected");
        }
    }

}
