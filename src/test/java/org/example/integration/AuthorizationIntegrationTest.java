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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AuthorizationIntegrationTest {
    static final DropwizardAppExtension<TestConfiguration> APP = new DropwizardAppExtension<>(
            TestApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getAllJobRoles_shouldReturn200_whenAuthorizedAsAdmin() {

        Client client = APP.client();

        LoginRequest loginRequest = new LoginRequest(
                "admin@kainos.com",
                "wlSNgEn5dCBM59jnbeH+txKWn36Vt6QScELcAa5ZBNduqSY16JAl2hqeGsZrmpG0kdb9+ILMoCJVB3er8ZoCJI9o26IM83UfnJtTT3p7cRgOUxsU0iMHgkI9KdQpDim6"

        );
        String token = "Bearer " + client
                .target(System.getenv("API_URL")+"auth/login")
                .request()
                .post(Entity.json(loginRequest))
                .readEntity(String.class);

        Response response = client
                .target(System.getenv("API_URL")+"job-roles/?page=1&limit=10")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void getAllJobRoles_shouldReturn200_whenAuthorizedAsUser() {

        Client client = APP.client();

        LoginRequest loginRequest = new LoginRequest(
                "user@kainos.com",
                "2FPs3siu6dkFvxlFgm9P8gUgBnnYEpjh8bs/RkiphJIs4T6yKz5vUyZPIbyeWFbsZaoU+Z9GXo+7RRMLfTZ8oPOdj9Z4Tjqcybmz+wNAg9sHukN0yIs/VROn8DR/LWcf"

        );
        String token = "Bearer " + client
                .target(System.getenv("API_URL")+"auth/login")
                .request()
                .post(Entity.json(loginRequest))
                .readEntity(String.class);

        Response response = client
                .target(System.getenv("API_URL")+"job-roles/?page=1&limit=10")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void getAllJobRoles_shouldReturn401Error_whenUnauthorized() {

        Client client = APP.client();

        Response response = client
                .target(System.getenv("API_URL")+"job-roles/?page=1&limit=10")
                .request()
                .get();

        Assertions.assertEquals(401, response.getStatus());

    }
}
