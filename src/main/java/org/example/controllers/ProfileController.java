package org.example.controllers;

import io.swagger.annotations.Api;
import org.example.models.ProfileRequest;
import org.example.services.ProfileService;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Profile API")
@Path("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(final ProfileService profileService) {
        this.profileService = profileService;
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(final ProfileRequest profileRequest) {
        try {
            profileService.updateProfile(profileRequest);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }
}
