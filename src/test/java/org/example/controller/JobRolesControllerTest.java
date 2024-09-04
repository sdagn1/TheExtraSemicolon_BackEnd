package org.example.controller;

import org.example.controllers.AuthController;
import org.example.controllers.JobRoleController;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidException;
import org.example.exceptions.InvalidPageLimitException;
import org.example.models.JobRoleResponse;
import org.example.services.AuthService;
import org.example.services.JobRoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
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

        Mockito.when(jobRoleService.getAllJobRoles()).thenReturn(jobRoleResponses);
        Response response = jobRoleController.getJobRoles();

        assertEquals(200, response.getStatus());
    }


    @Test
    void getJobRoles_shouldReturnServerError_whenServiceFailsToGetJobRoles() throws
            SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleService.getAllJobRoles()).thenThrow(SQLException.class);
        Response response = jobRoleController.getJobRoles();

        assertEquals(500, response.getStatus());
    }

    @Test
    void getJobRoles_shouldReturnBadRequest_whenRequestInvalid() throws
            SQLException, DoesNotExistException, InvalidPageLimitException {
        Mockito.when(jobRoleService.getAllJobRoles()).thenThrow(DoesNotExistException.class);
        Response response = jobRoleController.getJobRoles();

        assertEquals(404, response.getStatus());

    }
}
