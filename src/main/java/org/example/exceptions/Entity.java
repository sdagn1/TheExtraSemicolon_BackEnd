package org.example.exceptions;

public enum Entity {

    JOBROLERESPONSE("JobRoleResponse"),
    JOBROLE("JobRole"),
    USER("User"),
    FILE("File");

    private final String entity;

    Entity(final String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return this.entity;
    }
}
