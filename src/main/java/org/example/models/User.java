package org.example.models;

public class User {
    private String email;
    private String password;
    private String salt;
    private int roleId;

    public User(final String email, final String password,
                final String salt, final int roleId) {
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.roleId = roleId;

    }

    public User(final String email, final String salt) {
        this.email = email;
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(final int roleId) {
        this.roleId = roleId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }
}
