package org.example.validator;

import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.services.AuthService;
import org.example.services.JobRoleService;
import org.example.validators.ApplicationValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class ApplicationValidatorTest {

    AuthService authService = Mockito.mock(AuthService.class);
    JobRoleService jobRoleService = Mockito.mock(JobRoleService.class);

    ApplicationValidator applicationValidator =
            new ApplicationValidator(authService, jobRoleService);


    @Test
    public void validateApplication_shouldNotThrowError_whenValidApplication() throws
            SQLException, DoesNotExistException {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "user@kainos.com",
                1,
                "TestDocument.docx"
        );

        doNothing().when(authService).checkEmailExists(applicationRequest.getEmail());
        doNothing().when(jobRoleService).checkJobRoleIsValid(applicationRequest.getRoleId());


        assertDoesNotThrow(() -> applicationValidator.validateApplication(applicationRequest));

    }

    @Test
    public void validateApplication_shouldThrowDoesNotExistException_AuthServiceThrowsDoesNotExistException()
    throws SQLException, DoesNotExistException {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "user@kainos.com",
                1,
                "TestDocument.docx"
        );

        doThrow(DoesNotExistException.class).when(authService).checkEmailExists(applicationRequest.getEmail());
        doNothing().when(jobRoleService).checkJobRoleIsValid(applicationRequest.getRoleId());

        assertThrows(DoesNotExistException.class, () ->
                applicationValidator.validateApplication(applicationRequest));
    }

    @Test
    public void validateApplication_shouldThrowSQLException_AuthServiceThrowsSQLException()
            throws SQLException, DoesNotExistException {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "user@kainos.com",
                1,
                "TestDocument.docx"
        );

        doThrow(SQLException.class).when(authService).checkEmailExists(applicationRequest.getEmail());
        doNothing().when(jobRoleService).checkJobRoleIsValid(applicationRequest.getRoleId());

        assertThrows(SQLException.class, () ->
                applicationValidator.validateApplication(applicationRequest));
    }


    @Test
    public void validateApplication_shouldThrowDoesNotExistException_JobRoleServiceThrowsInvalidException()
            throws SQLException, DoesNotExistException {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "user@kainos.com",
                1,
                "TestDocument.docx"
        );

        doNothing().when(authService).checkEmailExists(applicationRequest.getEmail());
        doThrow(DoesNotExistException.class).when(jobRoleService).checkJobRoleIsValid(applicationRequest.getRoleId());

        assertThrows(DoesNotExistException.class, () ->
                applicationValidator.validateApplication(applicationRequest));
    }

    @Test
    public void validateApplication_shouldThrowSQLException_JobRoleServiceThrowsSQLException()
            throws SQLException, DoesNotExistException {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "user@kainos.com",
                1,
                "TestDocument.docx"
        );

        doNothing().when(authService).checkEmailExists(applicationRequest.getEmail());
        doThrow(SQLException.class).when(jobRoleService).checkJobRoleIsValid(applicationRequest.getRoleId());

        assertThrows(SQLException.class, () ->
                applicationValidator.validateApplication(applicationRequest));
    }

    @Test
    public void validateApplication_shouldThrowInvalidException_whenS3LinkEmpty() {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "user@kainos.com",
                1,
                ""
        );
        assertThrows(InvalidException.class, () ->
                applicationValidator.validateApplication(applicationRequest));

    }

}
