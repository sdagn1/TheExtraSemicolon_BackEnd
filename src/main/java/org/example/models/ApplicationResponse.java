package org.example.models;

public class ApplicationResponse {
    private String email;
    private int roleId;

    public ApplicationResponse(final String email, final int roleId) {
        this.email = email;
        this.roleId = roleId;
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
}
