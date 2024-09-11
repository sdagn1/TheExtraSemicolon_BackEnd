package org.example.service;

import org.example.daos.ApplicationDao;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.FailedToCreateException;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.models.ApplicationResponse;
import org.example.services.ApplicationService;
import org.example.validators.ApplicationValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doThrow;

public class ApplicationServiceTest {

    ApplicationDao applicationDao = Mockito.mock(ApplicationDao.class);
    ApplicationValidator applicationValidator = Mockito.mock(ApplicationValidator.class);

    ApplicationService applicationService = new ApplicationService(applicationDao, applicationValidator);

    ApplicationRequest applicationRequest = new ApplicationRequest(
            "user@kainos.com",
            1,
            "TestDocument.docx"
    );

    ApplicationResponse applicationResponse = new ApplicationResponse(
            "user@kainos.com",
            1
    );

    @Test
    public void createApplication_shouldReturnInt_whenDaoReturnsInt()
            throws SQLException, InvalidException, FailedToCreateException, DoesNotExistException {
        int rowsInserted = 1;
        Mockito.when(applicationDao.createApplication(applicationRequest)).thenReturn(rowsInserted);
        doNothing().when(applicationValidator).validateApplication(applicationRequest);

        assertEquals(rowsInserted,
                applicationService.createApplication(applicationRequest));
    }


    @Test
    public void createApplication_shouldThrowFailedToCreateException_whenNothingInserted()
    throws SQLException, InvalidException, DoesNotExistException {
        int rowsInserted = 0;
        Mockito.when(applicationDao.createApplication(applicationRequest)).thenReturn(rowsInserted);
        doNothing().when(applicationValidator).validateApplication(applicationRequest);
        assertThrows(FailedToCreateException.class, () ->
                applicationService.createApplication(applicationRequest));
    }

    @Test
    public void createApplication_shouldThrowSQLException_whenDaoThrowsSQLException()
    throws SQLException, InvalidException, DoesNotExistException {
        Mockito.when(applicationDao.createApplication(applicationRequest)).thenThrow(SQLException.class);
        doNothing().when(applicationValidator).validateApplication(applicationRequest);
        assertThrows(SQLException.class, () ->
                applicationService.createApplication(applicationRequest));
    }

    @Test
    public void createApplication_shouldThrowInvalidException_whenValidatorThrowsInvalidException()
            throws SQLException, InvalidException, DoesNotExistException{
        int rowsInserted = 1;
        Mockito.when(applicationDao.createApplication(applicationRequest)).thenReturn(rowsInserted);
        doThrow(InvalidException.class).when(applicationValidator).validateApplication(applicationRequest);
        assertThrows(InvalidException.class, () ->
                applicationService.createApplication(applicationRequest));
    }

    @Test
    public void createApplication_shouldThrowDoesNotExistException_whenValidatorThrowsDoesNotExistException()
            throws SQLException, InvalidException, DoesNotExistException {
        int rowsInserted = 1;
        Mockito.when(applicationDao.createApplication(applicationRequest)).thenReturn(rowsInserted);
        doThrow(DoesNotExistException.class).when(applicationValidator).validateApplication(applicationRequest);
        assertThrows(DoesNotExistException.class, () ->
                applicationService.createApplication(applicationRequest));
    }

    @Test
    public void getApplicationByIdEmail_shouldReturnApplicationResponse_whenDaoReturnsApplicationResponse()
    throws SQLException, DoesNotExistException {

        Mockito.when(applicationDao.getApplicationById(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail())).thenReturn(applicationResponse);

        ApplicationResponse mockedResponse = applicationService.getApplication(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail());

        assertEquals(applicationRequest.getEmail(), mockedResponse.getEmail());
        assertEquals(applicationRequest.getRoleId(), mockedResponse.getRoleId());

    }

    @Test
    public void getApplicationByIdEmail_shouldThrowDoesNotExistException_whenDaoReturnsNull()
            throws SQLException {

        Mockito.when(applicationDao.getApplicationById(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail())).thenReturn(null);



        assertThrows(DoesNotExistException.class, () ->
                applicationService.getApplication(applicationRequest.getRoleId(), applicationRequest.getEmail()));

    }

    @Test
    public void getApplicationByIdEmail_shouldThrowSQLException_whenDaoThrowsSQLException()
    throws SQLException {
        Mockito.when(applicationDao.getApplicationById(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail()
        )).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () ->
                applicationService.getApplication(
                        applicationRequest.getRoleId(),
                        applicationRequest.getEmail()));
    }
}
