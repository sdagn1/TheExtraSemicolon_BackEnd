package org.example.service;

import io.jsonwebtoken.Jwts;
import org.example.daos.AuthDao;
import org.example.exceptions.InvalidException;
import org.example.models.LoginRequest;
import org.example.models.User;
import org.example.services.AuthService;
import org.example.validators.LoginValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

class AuthServiceTests {

    AuthDao authDao = Mockito.mock(AuthDao.class);
    LoginValidator loginValidator = spy(new LoginValidator());
    Key jwtKey = Jwts.SIG.HS256.key().build();
    AuthService authService= new AuthService(authDao, loginValidator, jwtKey);

    LoginRequest loginRequest = new LoginRequest(
            "admin@kainos.com",
            "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6"
    );

    LoginRequest validLoginRequest = new LoginRequest(
            "admin@kainos.com",
            "dFupuh0ITKJyNJEEyHSWwHRd5Mnhm65I+WfTUZWbOh8I+akEWJ6VDsFsWetFCdA1EQP+Mx193tVd7MudxjAMoBYqusV78vypAzv/TDfKYVmZCVsco8q7hskFtsWnKVCf"

    );

    User user = new User(
            "admin@kainos.com",
            "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6",
            "salt",
            1
    );


    @Test
    void login_shouldThrowSqlException_whenDaoThrowsSqlException() throws SQLException {
        Mockito.when(authDao.getUser(loginRequest)).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> authService.login(loginRequest));
    }

    @Test
    void login_shouldReturnJwtToken_whenDaoReturnsUser() throws SQLException,
            NoSuchAlgorithmException, InvalidKeySpecException, InvalidException {
        Mockito.when(authDao.getUser(loginRequest)).thenReturn(user);
        Mockito.when(authDao.validateUser(validLoginRequest.getEmail(), validLoginRequest.getPassword())).thenReturn(user);

        assertNotNull(authService.login(loginRequest));
    }

    @Test
    void login_shouldThrowInvalidException_whenValidatorThrowsException() throws InvalidException {
        doThrow(InvalidException.class).when(loginValidator).validateLogin(loginRequest);

        assertThrows(InvalidException.class,
                () -> authService.login(loginRequest));
    }
}
