package org.example.controller;

import org.example.controllers.JobRoleController;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidException;
import org.example.models.JobRoleInfoResponse;
import org.example.models.JobRoleResponse;
import org.example.services.JobRoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobRolesControllerTest {


    JobRoleService jobRoleService = Mockito.mock(JobRoleService.class);

    private final JobRoleController jobRoleController = new JobRoleController(jobRoleService);


    @Test
    void GetJobRoles_shouldReturnSuccessfulResponse_whenOpenJobRolesReturned()
            throws
            SQLException, DoesNotExistException, InvalidException {

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

        String orderColumn = null;
        String orderStatement = null;
        Mockito.when(jobRoleService.getAllJobRoles(orderColumn, orderStatement))
                .thenReturn(jobRoleResponses);
        Response response = jobRoleController.getJobRoles(orderColumn,
                orderStatement);

        assertEquals(200, response.getStatus());
    }


    @Test
    void getJobRoles_shouldReturnServerError_whenServiceFailsToGetJobRoles()
            throws
            SQLException, DoesNotExistException, InvalidException {
        String orderColumn = null;
        String orderStatement = null;
        Mockito.when(jobRoleService.getAllJobRoles(orderColumn, orderStatement))
                .thenThrow(SQLException.class);
        Response response = jobRoleController.getJobRoles(orderColumn,
                orderStatement);

        assertEquals(500, response.getStatus());
    }

    @Test
    void getJobRoles_shouldReturnBadRequest_whenRequestInvalid() throws
            SQLException, DoesNotExistException, InvalidException {
        String orderColumn = null;
        String orderStatement = null;
        Mockito.when(jobRoleService.getAllJobRoles(orderColumn, orderStatement))
                .thenThrow(DoesNotExistException.class);
        Response response = jobRoleController.getJobRoles(orderColumn, orderStatement);

        assertEquals(404, response.getStatus());

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
    void getJobRoles_shouldBadRequest_whenInvalidOrderColumnGiven()
        throws SQLException, DoesNotExistException, InvalidException {
        String orderColumn = "fwwwrwrww";
        String orderStatement = "ASC";
        Mockito.when(jobRoleService.getAllJobRoles(orderColumn, orderStatement))
                .thenThrow(InvalidException.class);
        Response response = jobRoleController.getJobRoles(orderColumn, orderStatement);

        assertEquals(404, response.getStatus());
    }

    @Test
    void getJobRoles_shouldBadRequest_whenInvalidOrderStatementGiven()
            throws SQLException, DoesNotExistException, InvalidException {
        String orderColumn = "roleName";
        String orderStatement = "ASCII";
        Mockito.when(jobRoleService.getAllJobRoles(orderColumn, orderStatement))
                .thenThrow(InvalidException.class);
        Response response = jobRoleController.getJobRoles(orderColumn, orderStatement);

        assertEquals(404, response.getStatus());
    }

    @Test
    void getJobRoles_shouldReturnOrderedJobRolesByName() throws SQLException,
            DoesNotExistException, InvalidException {
        Date date = new Date();
        JobRoleResponse jobRoleResponse1 = new JobRoleResponse(
                1,
                "Technology Leader",
                Arrays.asList("Atlanta, Amsterdam, Belfast"),
                "Engineering",
                "Associate",
                date
        );

        JobRoleResponse jobRoleResponse2 = new JobRoleResponse(
                2,
                "Sales Development Consultant",
                Arrays.asList("Belfast", "Gdansk"),
                "Business Development and Marketing",
                "Consultant",
                date
        );

        JobRoleResponse jobRoleResponse3 = new JobRoleResponse(
                3,
                "Apprentice Software Engineer",
                Arrays.asList("Amsterdam"),
                "Engineering",
                "Apprentice",
                date
        );
        // Creating list with above jobRoleResponses in an array.
        List<JobRoleResponse> jobRoleResponses = Arrays.asList(jobRoleResponse1, jobRoleResponse2, jobRoleResponse3);
        Mockito.when(jobRoleService.getAllJobRoles("roleName", "ASC")).thenReturn(jobRoleResponses);

        Response response = jobRoleController.getJobRoles("roleName", "ASC");
        assertEquals(200, response.getStatus());

        List<JobRoleResponse> returnedJobRoleResponses = (List<JobRoleResponse>) response.getEntity();
        System.out.println(returnedJobRoleResponses.get(0).getRoleName());

        // CheckING if the list is in alphabetical order by roleName
        List<JobRoleResponse> sortedJobRoleResponses = new ArrayList<>(returnedJobRoleResponses);
        Collections.sort(sortedJobRoleResponses, Comparator.comparing(JobRoleResponse::getRoleName));
        System.out.println(sortedJobRoleResponses.get(0).getRoleName());
        int i = 0;
        for (int j = 0; j < returnedJobRoleResponses.size(); j++) {
            assertEquals(sortedJobRoleResponses.get(i).getRoleName(),
                    returnedJobRoleResponses.get(j).getRoleName());
            i++;
        }
    }

}
