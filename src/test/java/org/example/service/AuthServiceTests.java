package org.example.service;

import org.example.daos.AuthDao;
import org.example.daos.DatabaseConnector;
import org.example.models.LoginRequest;
import org.example.services.AuthService;
import org.example.validators.LoginValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.security.Key;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthServiceTests {

    AuthDao authDao = Mockito.mock(AuthDao.class);
    LoginValidator loginValidator = Mockito.mock(LoginValidator.class);
    Key jwtKey = Mockito.mock(Key.class);
    AuthService authService= new AuthService(authDao, loginValidator, jwtKey);

    LoginRequest loginRequest = new LoginRequest(
            "admin@kainos.com",
            "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6"
    );

    @Test
    void login_shouldThrowSqlException_whenDaoThrowsSqlException() throws SQLException {
        Mockito.when(authDao.getUser(loginRequest)).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> authService.login(loginRequest));
    }
}
