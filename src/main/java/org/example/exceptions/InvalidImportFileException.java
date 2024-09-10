package org.example.exceptions;

public class InvalidImportFileException extends Exception{
    public InvalidImportFileException(final Entity entity, final String reason) {
        super(entity.getEntity() + " is not valid: " + reason);
    }
}
