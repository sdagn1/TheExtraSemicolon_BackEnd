package org.example.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.example.exceptions.DoesNotExistException;
import org.example.models.ProfileRequest;
import org.example.models.ProfileResponse;
import org.example.models.UserRole;
import org.example.services.ProfileService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Profile API")
@Path("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(final ProfileService profileService) {
        this.profileService = profileService;
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(
            value = "Updates the User's Profile Picture",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION)
    )
    public Response updateProfile(final ProfileRequest profileRequest) {
        try {
            profileService.updateProfile(profileRequest);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(
            value = "Returns a Profile Picture",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = ProfileResponse.class
    )
    public Response getProfile(final String email) {
        try {
            return Response.ok()
                    .entity(profileService.getProfile(email)).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }
}
