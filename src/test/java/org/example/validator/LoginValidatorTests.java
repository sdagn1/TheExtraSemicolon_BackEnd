package org.example.validator;

import org.example.exceptions.InvalidException;
import org.example.models.LoginRequest;
import org.example.validators.LoginValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginValidatorTests {

    LoginValidator loginValidator = new LoginValidator();



    @Test
    public void validateLogin_shouldNotThrowError_whenValidLogin() {
        LoginRequest loginRequest = new LoginRequest(
                "admin@kainos.com",
                "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6"
        );

        assertDoesNotThrow(() -> loginValidator.validateLogin(loginRequest));

    }

    @Test
    public void validateLogin_shouldThrowError_whenEmailTooShort() {
        LoginRequest loginRequest = new LoginRequest(
                "",
                ""
        );


        assertThrows(InvalidException.class,
                () -> loginValidator.validateLogin(loginRequest));
    }

    @Test
    public void validateLogin_shouldThrowError_whenEmailTooLong() {
        LoginRequest loginRequest = new LoginRequest(
                "admin@kaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaainos.com",
                "admin"
        );


        assertThrows(InvalidException.class,
                () -> loginValidator.validateLogin(loginRequest));
    }

    @Test
    public void validateLogin_shouldThrowError_whenEmailInvalidFormat() {
        LoginRequest loginRequest = new LoginRequest(
                "test",
                "admin"
        );


        assertThrows(InvalidException.class,
                () -> loginValidator.validateLogin(loginRequest));
    }

    @Test
    public void validateLogin_shouldThrowError_whenPasswordTooShort() {
        LoginRequest loginRequest = new LoginRequest(
                "admin@kainos.com",
                ""
        );


        assertThrows(InvalidException.class,
                () -> loginValidator.validateLogin(loginRequest));
    }

    @Test
    public void validateLogin_shouldThrowError_whenPasswordTooLong() {
        LoginRequest loginRequest = new LoginRequest(
                "admin@kainos.com",
                "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6"
        );


        assertThrows(InvalidException.class,
                () -> loginValidator.validateLogin(loginRequest));
    }
}
