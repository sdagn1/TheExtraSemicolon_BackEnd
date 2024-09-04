package org.example.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.example.exceptions.DoesNotExistException;
import org.example.models.UserRole;
import org.example.services.JobRoleService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

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
    public Response getJobRoles() {
        try {
            return Response.ok()
                    .entity(jobRoleService.getAllJobRoles())
                    .build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }
}
