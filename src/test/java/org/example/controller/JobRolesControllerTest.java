package org.example.controller;

import org.example.controllers.JobRoleController;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidPageLimitException;
import org.example.models.JobRoleInfoResponse;
import org.example.models.JobRoleResponse;
import org.example.services.JobRoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.ws.rs.core.HttpHeaders;

import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobRolesControllerTest {


    JobRoleService jobRoleService = Mockito.mock(JobRoleService.class);

    private final JobRoleController jobRoleController = new JobRoleController(jobRoleService);


    @Test
    void GetJobRoles_shouldReturnSuccessfulResponse_whenOpenJobRolesReturned() throws
            SQLException, DoesNotExistException, InvalidPageLimitException {

        List<String> locations = new ArrayList<>();
        locations.add("Atlanta");
        locations.add("Amsterdam");
        locations.add("Belfast");

        Date date = new Date();

        JobRoleResponse jobRoleResponse = new JobRoleResponse(
                1,
                "Technology Leader",
                locations,
                "Engineering",
                "Associate",
                date
        );

        List<JobRoleResponse> jobRoleResponses = new ArrayList<>();
        jobRoleResponses.add(jobRoleResponse);

        Mockito.when(jobRoleService.getAllJobRoles(1,10)).thenReturn(jobRoleResponses);
        Response response = jobRoleController.getJobRoles(1,10);

        assertEquals(200, response.getStatus());
    }


    @Test
    void getJobRoles_shouldReturnServerError_whenServiceFailsToGetJobRoles() throws
            SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleService.getAllJobRoles(1,10)).thenThrow(SQLException.class);
        Response response = jobRoleController.getJobRoles(1,10);

        assertEquals(500, response.getStatus());
    }

    @Test
    void getJobRoles_shouldReturnBadRequest_whenRequestInvalid() throws
            SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleService.getAllJobRoles(1,10)).thenThrow(DoesNotExistException.class);
        Response response = jobRoleController.getJobRoles(1,10);

        assertEquals(404, response.getStatus());

    }

    @Test
    void getJobRoles_shouldReturnInvalidPageLimitException_whenPageInvalid() throws
            SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleService.getAllJobRoles(100,10)).thenThrow(InvalidPageLimitException.class);
        Response response = jobRoleController.getJobRoles(100,10);

        assertEquals(400, response.getStatus());

    }

    @Test
    void getJobRoles_shouldReturnInvalidPageLimitException_whenLimitInvalid() throws
            SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleService.getAllJobRoles(1,11)).thenThrow(InvalidPageLimitException.class);
        Response response = jobRoleController.getJobRoles(1,11);

        assertEquals(400, response.getStatus());

    }

    @Test
    void getJobRoleById_shouldReturnSuccessfulResponseWhenJobRoleReturned()
    throws SQLException, DoesNotExistException {
        Date date = new Date();
        JobRoleInfoResponse jobRoleInfoResponse = new JobRoleInfoResponse(
                1,
                "Technology Leader",
                "Test description for technology leader",
                "Responsibility 1, 2, 3, 4, 5",
                "Atlanta, Amsterdam, Belfast",
                "linkToJobSpecHere",
                "Engineering"
        );
        jobRoleInfoResponse.setBand("associate");
        jobRoleInfoResponse.setClosingDate(date);
        jobRoleInfoResponse.setStatus(true);
        jobRoleInfoResponse.setPositionsAvailable(2);

        Mockito.when(jobRoleService.getJobRoleById(2)).thenReturn(
                jobRoleInfoResponse);
        Response response = jobRoleController.getJobRoleById(2);

        assertEquals(200, response.getStatus());
    }

    @Test
    void getJobRoleById_shouldReturnServerError_whenServiceFailsToGetJobRoleById() throws
            SQLException, DoesNotExistException {
        Mockito.when(jobRoleService.getJobRoleById(2)).thenThrow(SQLException.class);
        Response response = jobRoleController.getJobRoleById(2);

        assertEquals(500, response.getStatus());
    }

    @Test
    void getJobRoleById_shouldReturnBadRequest_whenRequestInvalid() throws
            SQLException, DoesNotExistException  {
        Mockito.when(jobRoleService.getJobRoleById(2)).thenThrow(DoesNotExistException.class);
        Response response = jobRoleController.getJobRoleById(2);

        assertEquals(404, response.getStatus());
    }

    @Test
    void getFullJobRoles_shouldReturnDoesNotExistException_whenJobRolesDoesNotExist() throws
            SQLException, DoesNotExistException, IOException {
        Mockito.when(jobRoleService.getFullJobRoles()).thenThrow(DoesNotExistException.class);
        Response response = jobRoleController.getFullJobRole();

        assertEquals(404, response.getStatus());
    }
    @Test
    void getFullJobRoles_shouldReturnIOException_whenIOExceptionThrown() throws
            SQLException, DoesNotExistException, IOException {
        Mockito.when(jobRoleService.getFullJobRoles()).thenThrow(IOException.class);
        Response response = jobRoleController.getFullJobRole();

        assertEquals(500, response.getStatus());
    }
    @Test
    void getFullJobRoles_shouldReturnSQLException_whenSQLExceptionThrown() throws
            SQLException, DoesNotExistException, IOException {
        Mockito.when(jobRoleService.getFullJobRoles()).thenThrow(SQLException.class);
        Response response = jobRoleController.getFullJobRole();

        assertEquals(500, response.getStatus());
    }
    @Test
    void getFullJobRoles_shouldReturnOk_whenJobRolesExist() throws
            SQLException, DoesNotExistException, IOException {
        FileInputStream mockFileInputStream = Mockito.mock(FileInputStream.class);

        Mockito.when(jobRoleService.getFullJobRoles()).thenReturn(mockFileInputStream);

        Response response = jobRoleController.getFullJobRole();

        assertEquals(200, response.getStatus());
        assertEquals("attachment; filename=\"Report.csv\"", response.getHeaderString(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(mockFileInputStream, response.getEntity());
    }


}
