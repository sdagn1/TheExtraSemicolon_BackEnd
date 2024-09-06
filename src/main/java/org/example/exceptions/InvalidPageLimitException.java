package org.example.exceptions;

public class InvalidPageLimitException extends Exception {
    public InvalidPageLimitException(final Entity entity, final String reason) {
        super(entity.getEntity() + " is not valid: " + reason);
    }
}
