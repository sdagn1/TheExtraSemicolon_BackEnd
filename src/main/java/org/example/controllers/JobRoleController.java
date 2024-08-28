package org.example.controllers;
import io.swagger.annotations.Api;
import org.example.exceptions.DoesNotExistException;
import org.example.services.JobRoleService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("TheExtraSemicolon - JobRoles")
@Path("/api/job-roles")
public class JobRoleController {
    JobRoleService jobRoleService;

    public JobRoleController(final JobRoleService jobRoleService) {
        this.jobRoleService = jobRoleService;
    }

    @GET
    @Path("/full")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
//    @ApiOperation(
//            value = "Returns a List of all Job roles with all fields",
//            authorizations = @Authorization(value =
//            HttpHeaders.AUTHORIZATION),
//            response = JobRole.class
//    )
    public Response getJobRolesFull() {
        try {
            return Response.ok().entity(jobRoleService.getAllJobRolesFull())
                    .build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
//    @ApiOperation(
//            value = "Returns a Job Role",
//            authorizations = @Authorization(value
//            = HttpHeaders.AUTHORIZATION),
//            response = JobRole.class
//    )
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
}
