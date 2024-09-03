package org.example.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.example.TestApplication;
import org.example.TestConfiguration;
import org.example.models.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class LoginIntegrationTest {
    static final DropwizardAppExtension<TestConfiguration> APP = new DropwizardAppExtension<>(
            TestApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void login_shouldExpectToken_whenLoginValid() {
        LoginRequest loginRequest = new LoginRequest(
                "admin@kainos.com",
                "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6"

        );

        Client client = APP.client();
        Response response = client
                .target(System.getenv("API_URL")+"auth/login")
                .request()
                .post(Entity.json(loginRequest));

        Assertions.assertEquals(response.getStatus(), 200);
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "'', ''",
                    "admin@kaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaainos.com, ''",
                    "test, ''",
                    "admin@kainos.com, ''",
                    "admin@kainos.com, wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6",
                    "admin@kainos.com, test"

            }
    )
    void login_shouldExpectResponseOf400_whenLoginRequestInvalid(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(
                email,
                password
        );

        Client client = APP.client();

        Response response = client
                .target(System.getenv("API_URL")+"auth/login")
                .request()
                .post(Entity.json(loginRequest));

        Assertions.assertEquals(response.getStatus(), 400);
    }

    @Test
    void login_shouldExpectResponseOf404_whenAddressInvalid() {
        LoginRequest loginRequest = new LoginRequest(
                "admin@kainos.com",
                "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6"

        );

        Client client = APP.client();

        Response response = client
                .target(System.getenv("API_URL")+"/auth/login")
                .request()
                .post(Entity.json(loginRequest));

        Assertions.assertEquals(response.getStatus(), 404);
    }

}
