package org.example.controller;

import org.example.controllers.ProfileController;
import org.example.exceptions.DoesNotExistException;
import org.example.models.ProfileRequest;
import org.example.models.ProfileResponse;
import org.example.services.ProfileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class ProfileControllerTest {

    ProfileService profileService = Mockito.mock(ProfileService.class);

    ProfileController profileController = new ProfileController(profileService);


    ProfileRequest profileRequest = new ProfileRequest(
            "test@kainos.com",
            "testProfilePicture"
    );

    ProfileResponse profileResponse = new ProfileResponse(
            "testProfilePicture"
    );
    String email = "test@kainos.com";


    @Test
    public void updateProfile_shouldReturn204Response_whenUpdateSuccessful()
    throws SQLException {
        doNothing().when(profileService).updateProfile(profileRequest);

        Response response = profileController.updateProfile(profileRequest);

        assertEquals(204, response.getStatus());
    }

    @Test
    public void updateProfile_shouldReturn500Response_whenServiceThrowsSQLException()
    throws SQLException {
        doThrow(SQLException.class).when(profileService).updateProfile(profileRequest);

        Response response = profileController.updateProfile(profileRequest);

        assertEquals(500, response.getStatus());
    }

    @Test
    public void getProfile_shouldReturn200Response_whenServiceReturnsProfileResponse()
    throws SQLException, DoesNotExistException {
        Mockito.when(profileService.getProfile(email)).thenReturn(profileResponse);

        Response response = profileController.getProfile(email);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void getProfile_shouldReturn500Response_whenServiceThrowsSQLException()
    throws SQLException, DoesNotExistException {
        Mockito.when(profileService.getProfile(email)).thenThrow(SQLException.class);

        Response response = profileController.getProfile(email);

        assertEquals(500, response.getStatus());

    }

    @Test
    public void getProfile_shouldReturn404Response_whenServiceThrowsDoesNotExistException()
            throws SQLException, DoesNotExistException {
        Mockito.when(profileService.getProfile(email)).thenThrow(DoesNotExistException.class);

        Response response = profileController.getProfile(email);

        assertEquals(404, response.getStatus());

    }
}
