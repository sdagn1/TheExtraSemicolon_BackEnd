package org.example.controllers;

import io.swagger.annotations.Api;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidPageLimitException;
import org.example.models.JobRoleResponse;
import org.example.services.JobRoleService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("job-roles")
@Path("/api/job-roles")
public class JobRoleController {

    JobRoleService jobRoleService;

    public JobRoleController(final JobRoleService jobRoleService) {
        this.jobRoleService = jobRoleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobRoles(final @QueryParam("page") int page,
                                final @QueryParam("limit") int limit)
            throws SQLException {
        try {
            List<JobRoleResponse> jobRoles = jobRoleService.
                                    getAllJobRoles(page, limit);
            int total = jobRoleService.getTotalJobRoles();
            int pages = (int) Math.ceil((double) total / limit);


            Map<String, Object> response = new HashMap<>();
            response.put("jobRoles", jobRoles);
            response.put("pagination", Map.of(
                    "total", total,
                    "limit", limit,
                    "page", page,
                    "pages", pages
            ));

            return Response.ok().entity(response).build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (InvalidPageLimitException e) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(e.getMessage()).build();
        }
    }
}
