package org.example.validator;

import org.example.exceptions.InvalidException;
import org.example.models.LoginRequest;
import org.example.validators.LoginValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginValidatorTest {

    LoginValidator loginValidator = new LoginValidator();



    @Test
    public void validateLogin_shouldNotThrowError_whenValidLogin() {
        LoginRequest loginRequest = new LoginRequest(
                "admin@kainos.com",
                "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6"
        );

        assertDoesNotThrow(() -> loginValidator.validateLogin(loginRequest));

    }


    @ParameterizedTest
    @CsvSource(
            {
                    "'', No email entered",
                    "admin@kaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaainos.com, Email is invalid length",
                    "test, Email is invalid format"
            }
    )
    public void validateLogin_shouldThrowError_whenEmailInvalid(String email, String exceptionMsg) {
        LoginRequest loginRequest = new LoginRequest(
                email,
                ""
        );

        InvalidException exception = assertThrows(InvalidException.class,
                () -> loginValidator.validateLogin(loginRequest));

        assertEquals("User is not valid: "+exceptionMsg, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "'', Password is invalid length",
                    "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6, Password is invalid length"

            }
    )
    public void validateLogin_shouldThrowError_whenPasswordInvalid(String password, String exceptionMsg) {
        LoginRequest loginRequest = new LoginRequest(
                "admin@kainos.com",
                password
        );

        InvalidException exception = assertThrows(InvalidException.class,
                () -> loginValidator.validateLogin(loginRequest));

        assertEquals("User is not valid: "+exceptionMsg, exception.getMessage());
    }
}
