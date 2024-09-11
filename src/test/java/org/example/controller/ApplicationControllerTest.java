package org.example.controller;

import org.example.controllers.ApplicationController;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.FailedToCreateException;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.models.ApplicationResponse;
import org.example.services.ApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ApplicationControllerTest {

    ApplicationService applicationService = Mockito.mock(ApplicationService.class);

    ApplicationController applicationController = new ApplicationController(applicationService);

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
    public void createApplication_shouldReturn201Response_whenServiceReturnsInt() throws
            SQLException, FailedToCreateException, InvalidException,
            DoesNotExistException {
        Mockito.when(applicationService.createApplication(applicationRequest)).thenReturn(1);
        Response response = applicationController.createApplication(applicationRequest);
        assertEquals(201, response.getStatus());
    }

    @Test
    public void createApplication_shouldReturn500Response_whenServiceThrowsSQLException() throws
            SQLException, FailedToCreateException, InvalidException, DoesNotExistException{
        Mockito.when(applicationService.createApplication(applicationRequest)).thenThrow(SQLException.class);

        Response response = applicationController.createApplication(applicationRequest);

        assertEquals(500, response.getStatus());
    }

    @Test
    public void createApplication_shouldReturn500Response_whenServiceThrowsFailedToCreatedException() throws
            SQLException, FailedToCreateException, InvalidException, DoesNotExistException{
        Mockito.when(applicationService.createApplication(applicationRequest)).thenThrow(
                FailedToCreateException.class);

        Response response = applicationController.createApplication(applicationRequest);

        assertEquals(500, response.getStatus());
    }


    @Test
    public void createApplication_shouldReturn400Response_whenServiceThrowsInvalidException() throws
            SQLException, FailedToCreateException, InvalidException, DoesNotExistException{
        Mockito.when(applicationService.createApplication(applicationRequest)).thenThrow(
                InvalidException.class);

        Response response = applicationController.createApplication(applicationRequest);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void createApplication_shouldReturn400Response_whenServiceThrowsDoesNotExistException() throws
            SQLException, FailedToCreateException, InvalidException, DoesNotExistException{
        Mockito.when(applicationService.createApplication(applicationRequest)).thenThrow(
                DoesNotExistException.class);

        Response response = applicationController.createApplication(applicationRequest);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void getSingleApplication_shouldReturn200Response_whenServiceReturnsApplicationResponse()
        throws SQLException, DoesNotExistException {

        Mockito.when(applicationService.getApplication(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail()))
                .thenReturn(applicationResponse);

        Response response = applicationController.getSingleApplication(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail()
        );

        assertEquals(200, response.getStatus());

    }

    @Test
    public void getSingleApplication_shouldReturn404Response_whenServiceThrowsDoesNotExistException()
    throws SQLException, DoesNotExistException {
        Mockito.when(applicationService.getApplication(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail()
        )).thenThrow(DoesNotExistException.class);

        Response response = applicationController.getSingleApplication(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail()
        );

        assertEquals(404, response.getStatus());
    }

    @Test
    public void getSingleApplication_shouldReturn500Response_whenServiceThrowsSQLException()
            throws SQLException, DoesNotExistException {
        Mockito.when(applicationService.getApplication(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail()
        )).thenThrow(SQLException.class);

        Response response = applicationController.getSingleApplication(
                applicationRequest.getRoleId(),
                applicationRequest.getEmail()
        );

        assertEquals(500, response.getStatus());
    }
}
