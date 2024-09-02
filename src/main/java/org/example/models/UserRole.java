package org.example.models;

import java.util.Map;

public class UserRole {
    private static final String ADMIN = "admin";
    private static final String USER = "user";

    int roleId;

    private static final Map<Integer, String> ROLESMAP = Map.of(
            1, ADMIN,
            2, USER
    );

    public UserRole(final int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return ROLESMAP.get(getRoleId());
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(final int roleId) {
        this.roleId = roleId;
    }
}
