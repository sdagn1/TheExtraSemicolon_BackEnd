package org.example.services;

import org.example.daos.ProfileDao;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.models.ProfileRequest;
import org.example.models.ProfileResponse;

import java.sql.SQLException;

public class ProfileService {

    private final ProfileDao profileDao;

    public ProfileService(final ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public ProfileResponse getProfile(final String email) throws
            SQLException, DoesNotExistException {
        ProfileResponse profileResponse = profileDao.getProfile(email);
        if (profileResponse == null) {
            throw new DoesNotExistException(Entity.PROFILE);
        }
        return profileResponse;
    }

    public void updateProfile(final ProfileRequest profileRequest)
    throws SQLException {
        profileDao.updateProfile(profileRequest);
    }
}
