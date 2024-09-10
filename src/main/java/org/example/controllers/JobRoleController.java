package org.example.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidPageLimitException;
import org.example.models.JobRoleResponse;
import org.example.models.UserRole;
import org.example.services.JobRoleService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.HttpHeaders;

@Api("job-roles")
@Path("/api/job-roles")
public class JobRoleController {
    JobRoleService jobRoleService;

    public JobRoleController(final JobRoleService jobRoleService) {
        this.jobRoleService = jobRoleService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(
            value = "Returns a Job Role",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = List.class
    )
    public Response getJobRoleById(final @PathParam("id") int id) {
        try {
            return Response.ok().entity(jobRoleService.getJobRoleById(id))
                    .build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(
            value = "Returns a List of Job Role Responses",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = List.class
    )
    public Response getJobRoles(final @QueryParam("page") int page,
                                final @QueryParam("limit") int limit) {
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
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (InvalidPageLimitException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/report")
    @Produces(MediaType.TEXT_PLAIN)
//    @RolesAllowed({UserRole.ADMIN})
    @ApiOperation(
            value = "Returns a Report of All Job Roles",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = List.class
    )
    public Response getFullJobRole() {
        try {
            return Response.ok(jobRoleService.getFullJobRoles())
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\""
                                    + jobRoleService.file.getName()
                                    + "\"")
                    .build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (InvalidPageLimitException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
