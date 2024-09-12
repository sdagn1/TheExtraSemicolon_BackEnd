package org.example.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileRequest {
    private String email;
    private String profilePicture;

    @JsonCreator
    public ProfileRequest(
            @JsonProperty("email") final String email,
            @JsonProperty("profilePicture") final String profilePicture) {
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(final String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
