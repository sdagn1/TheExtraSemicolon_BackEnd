package org.example.exceptions;

public enum Entity {
    JOBROLERESPONSE("JobRoleResponse"),
    USER("User"),
    APPLICATION("Application"),
    JOBROLE("JobRole");

    private final String entity;

    Entity(final String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return this.entity;
    }
}
