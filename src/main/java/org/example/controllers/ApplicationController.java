package org.example.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.FailedToCreateException;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.models.ApplicationResponse;
import org.example.models.UserRole;
import org.example.services.ApplicationService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Application API")
@Path("/api/apply")
public class ApplicationController {
    private ApplicationService applicationService;

    public ApplicationController(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(
            value = "Creates an Application",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Integer.class
    )
    public Response createApplication(
            final ApplicationRequest applicationRequest) {
        try {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(applicationService
                            .createApplication(applicationRequest))
                    .build();
        } catch (FailedToCreateException | SQLException e) {
            return Response.serverError().build();
        } catch (DoesNotExistException | InvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();

        }
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(
            value = "Returns an Application",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = ApplicationResponse.class
    )
    public Response getSingleApplication(
            final @PathParam("id") int id,
            final String email) {
        try {
            return Response.ok().entity(applicationService
                    .getApplication(id, email))
                    .build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/delete/{id}")
    @RolesAllowed({UserRole.ADMIN})
    public Response deleteApplication(
            final @PathParam("id") int id,
            final String email
    ) {
        try {
            applicationService.deleteApplication(id, email);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

}

