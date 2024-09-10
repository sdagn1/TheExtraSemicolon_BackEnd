package org.example.services;

import org.example.daos.ProfileDao;
import org.example.models.ProfileRequest;

import java.sql.SQLException;

public class ProfileService {

    private final ProfileDao profileDao;

    public ProfileService(final ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public void updateProfile(final ProfileRequest profileRequest)
    throws SQLException {
        profileDao.updateProfile(profileRequest);
    }
}
