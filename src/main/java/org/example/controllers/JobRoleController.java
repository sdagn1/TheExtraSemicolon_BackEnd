package org.example.controllers;

import io.swagger.annotations.Api;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidException;
import org.example.services.JobRoleService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

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
    public Response getJobRoles(final @QueryParam("orderColumn") String orderColumn,
                                final @QueryParam("orderStatement") String orderStatement) {
        try {
            System.out.println(orderColumn);
            System.out.println(orderStatement);
            return Response.ok()
                    .entity(jobRoleService.getAllJobRoles(orderColumn, orderStatement))
                    .build();
        } catch (DoesNotExistException | InvalidException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }
}
