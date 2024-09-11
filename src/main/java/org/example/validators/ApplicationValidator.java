package org.example.validators;

import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.services.AuthService;
import org.example.services.JobRoleService;

import java.sql.SQLException;

public class ApplicationValidator {
    private final AuthService authService;
    private final JobRoleService jobRoleService;


    public ApplicationValidator(final AuthService authService,
                                final JobRoleService jobRoleService) {
        this.authService = authService;
        this.jobRoleService = jobRoleService;
    }

    public void validateApplication(
            final ApplicationRequest applicationRequest) throws
            InvalidException, SQLException, DoesNotExistException {
        if (applicationRequest.getS3Link().isEmpty()) {
            throw new InvalidException(Entity.APPLICATION,
                    "Something went wrong when uploading your CV");
        }

        authService.checkEmailExists(applicationRequest.getEmail());
        jobRoleService.checkJobRoleIsValid(applicationRequest.getRoleId());

    }
}
