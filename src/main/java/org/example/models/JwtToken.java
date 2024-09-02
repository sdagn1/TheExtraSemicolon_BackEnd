package org.example.models;

import javax.security.auth.Subject;
import java.security.Principal;

public class JwtToken implements Principal {

    UserRole userRole;

    public JwtToken(final UserRole userRole) {
        setUserRole(userRole);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(final Subject subject) {
        return Principal.super.implies(subject);
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(final UserRole userRole) {
        this.userRole = userRole;
    }
}
