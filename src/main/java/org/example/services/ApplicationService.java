package org.example.services;

import org.example.daos.ApplicationDao;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.exceptions.FailedToCreateException;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.models.ApplicationResponse;
import org.example.validators.ApplicationValidator;

import java.sql.SQLException;

public class ApplicationService {
    private final ApplicationDao applicationDao;
    private final ApplicationValidator applicationValidator;

    public ApplicationService(final ApplicationDao applicationDao,
                              final ApplicationValidator applicationValidator) {
        this.applicationDao = applicationDao;
        this.applicationValidator = applicationValidator;
    }

    public int createApplication(final ApplicationRequest applicationRequest)
    throws SQLException, FailedToCreateException, InvalidException,
            DoesNotExistException {
        applicationValidator.validateApplication(applicationRequest);
        int rowsInserted = applicationDao.createApplication(applicationRequest);
        if (rowsInserted <= 0) {
            throw new FailedToCreateException(Entity.APPLICATION);
        }

        return rowsInserted;
    }

    public ApplicationResponse getApplication(final int roleId,
                                                  final String email)
    throws SQLException, DoesNotExistException {
        ApplicationResponse applicationResponse = applicationDao
                .getApplicationById(roleId, email);
        if (applicationResponse == null) {
            throw new DoesNotExistException(Entity.APPLICATION);
        }

        return applicationResponse;

    }

    public void deleteApplication(final int roleId, final String email)
    throws SQLException, DoesNotExistException {
        ApplicationResponse applicationToDelete =
                getApplication(roleId, email);

        if (applicationToDelete == null) {
            throw new DoesNotExistException(Entity.APPLICATION);
        }

        applicationDao.deleteApplication(roleId, email);
    }
}
