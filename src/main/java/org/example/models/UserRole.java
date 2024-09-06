package org.example.models;

import java.util.Map;

public class UserRole {
    public static final String ADMIN = "admin";
    public static final String USER = "user";

    private int roleId;

    private static final Map<Integer, String> ROLESMAP = Map.of(
            1, ADMIN,
            2, USER
    );

    public UserRole(final int roleId) {
        setRoleId(roleId);
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
