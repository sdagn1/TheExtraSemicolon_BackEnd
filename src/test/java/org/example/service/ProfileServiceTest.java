package org.example.service;

import org.example.daos.ProfileDao;
import org.example.exceptions.DoesNotExistException;
import org.example.models.ProfileRequest;
import org.example.models.ProfileResponse;
import org.example.services.ProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class ProfileServiceTest {

    ProfileDao profileDao = Mockito.mock(ProfileDao.class);

    ProfileService profileService = new ProfileService(profileDao);

    ProfileRequest profileRequest = new ProfileRequest(
            "test@kainos.com",
            "testProfilePicture"
    );

    ProfileResponse profileResponse = new ProfileResponse(
            "testProfilePicture"
    );
    String email = "test@kainos.com";

    @Test
    public void getProfile_shouldReturnProfileResponse_whenDaoReturnsProfileResponse()
    throws SQLException, DoesNotExistException {

        Mockito.when(profileDao.getProfile(email)).thenReturn(profileResponse);

        ProfileResponse mockedResponse = profileService.getProfile(email);

        assertEquals(mockedResponse.getProfilePicture(), profileResponse.getProfilePicture());
    }


    @Test
    public void getProfile_shouldThrowSQLException_whenDaoThrowsSQLException()
    throws SQLException {
        Mockito.when(profileDao.getProfile(email)).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () ->
                profileService.getProfile(email));
    }

    @Test
    public void getProfile_shouldThrowDoesNotExistException_whenDaoReturnsNull()
    throws SQLException {
        Mockito.when(profileDao.getProfile(email)).thenReturn(null);

        assertThrows(DoesNotExistException.class, () ->
                profileService.getProfile(email));
    }

    @Test
    public void updateProfile_shouldNotThrowException_whenDaoDoesNotThrowException()
    throws SQLException {
        doNothing().when(profileDao).updateProfile(profileRequest);

        Assertions.assertDoesNotThrow(() -> profileService.updateProfile(profileRequest));
    }

    @Test
    public void updateProfile_shouldThrowSQLException_whenDaoDoesNotThrowException()
    throws SQLException {
        doThrow(SQLException.class).when(profileDao).updateProfile(profileRequest);

        assertThrows(SQLException.class, () ->
                profileService.updateProfile(profileRequest));
    }
}
