package org.example.exceptions;

public class FailedToCreateException extends Throwable {
    public FailedToCreateException(final Entity entity) {
        super("Failed to create " + entity.getEntity());
    }
}
