package org.example.validators;

import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.LoginRequest;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LoginValidator {
    private final int emailLength = 255;
    private final int passwordHashLength = 128;
    private final String emailCheck = "^(?=.{1,64}@)[A-Za-z0-9_-]+"
            +
            "(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public void validateLogin(final LoginRequest loginRequest)
            throws InvalidException {
        if (loginRequest.getEmail().length() > emailLength
        || loginRequest.getEmail().isEmpty()) {
            throw new InvalidException(Entity.USER, "Email is invalid length");
        }
        Pattern pattern = Pattern.compile(emailCheck);
        Matcher matcher = pattern.matcher(loginRequest.getEmail());
        if (!matcher.matches()) {
            throw new InvalidException(Entity.USER,
                    "Email is invalid!");
        }

        if (loginRequest.getPassword().length() != passwordHashLength) {
            throw new InvalidException(Entity.USER,
                    "Password is invalid length");
        }

    }
}
