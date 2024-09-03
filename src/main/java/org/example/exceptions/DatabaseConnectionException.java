package org.example.exceptions;

public class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(final Exception e) {
        super(e);
    }
}
