package org.example.controller;

import org.example.controllers.AuthController;
import org.example.exceptions.InvalidException;
import org.example.models.LoginRequest;
import org.example.services.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthTest {

    AuthService authService = Mockito.mock(AuthService.class);

    private final AuthController authController = new AuthController(authService);

    LoginRequest loginRequest = new LoginRequest(
            "admin@kainos.com",
            "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6"
    );

    @Test
    void login_shouldReturnSuccessfulResponse_whenLoginReturnsToken() throws
            InvalidException, NoSuchAlgorithmException,
            InvalidKeySpecException, SQLException {
        Mockito.when(authService.login(loginRequest)).thenReturn("");
        Response response = authController.login(loginRequest);

        assertEquals(200, response.getStatus());
    }

    @Test
    void login_shouldReturnServerError_whenServiceFailsToLogin() throws
            InvalidException, NoSuchAlgorithmException,
            InvalidKeySpecException, SQLException  {
        Mockito.when(authService.login(loginRequest)).thenThrow(SQLException.class);
        Response response = authController.login(loginRequest);

        assertEquals(500, response.getStatus());
    }

    @Test
    void login_shouldReturnBadRequest_whenRequestInvalid() throws
            InvalidException, NoSuchAlgorithmException,
            InvalidKeySpecException, SQLException  {
        Mockito.when(authService.login(loginRequest)).thenThrow(InvalidException.class);
        Response response = authController.login(loginRequest);

        assertEquals(400, response.getStatus());

    }
}
