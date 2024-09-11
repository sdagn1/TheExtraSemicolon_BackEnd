package org.example.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationRequest {
    private String email;
    private int roleId;
    private String s3Link;

    @JsonCreator
    public ApplicationRequest(@JsonProperty("email") final String email,
                              @JsonProperty("roleId") final int roleId,
                              @JsonProperty("S3Link") final String s3Link) {
        this.email = email;
        this.roleId = roleId;
        this.s3Link = s3Link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(final int roleId) {
        this.roleId = roleId;
    }

    public String getS3Link() {
        return s3Link;
    }

    public void setS3Link(final String s3Link) {
        this.s3Link = s3Link;
    }
}
