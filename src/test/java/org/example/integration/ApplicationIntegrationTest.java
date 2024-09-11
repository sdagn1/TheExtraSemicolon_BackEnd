package org.example.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.example.TestApplication;
import org.example.TestConfiguration;
import org.example.models.ApplicationRequest;
import org.example.models.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ApplicationIntegrationTest {
    static final DropwizardAppExtension<TestConfiguration> APP = new DropwizardAppExtension<>(
            TestApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    public void getSingleApplication_shouldReturn200_whenGettingExistingApplication() {
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
                .target(System.getenv("API_URL")+"apply/1")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json("user@kainos.com"));

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    public void getSingleApplication_shouldReturn401_whenUnauthenticated() {
        Client client = APP.client();

        Response response = client
                .target(System.getenv("API_URL")+"apply/1")
                .request()
                .post(Entity.json("user@kainos.com"));

        Assertions.assertEquals(401, response.getStatus());

    }

    @Test
    public void getSingleApplication_shouldReturn404_whenApplicationDoesNotExist() {
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
                .target(System.getenv("API_URL")+"apply/-1")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json("user@kainos.com"));

        Assertions.assertEquals(404, response.getStatus());

    }

    @Test
    public void createApplication_shouldReturn201_whenCreatingSuccessfulApplication() {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "admin@kainos.com",
                1,
                "TestDocument.docx"
        );

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
                .target(System.getenv("API_URL")+"apply")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(applicationRequest));

        Assertions.assertEquals(201, response.getStatus());

        Response deleteResponse = client
                .target(System.getenv("API_URL")+"apply/delete/"+applicationRequest.getRoleId())
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(applicationRequest.getEmail()));

        Assertions.assertEquals(204, deleteResponse.getStatus());

    }

    @Test
    public void createApplication_shouldReturn500_whenCreatingExistingApplication() {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "user@kainos.com",
                1,
                "TestDocument.docx"
        );

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
                .target(System.getenv("API_URL")+"apply")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(applicationRequest));

        Assertions.assertEquals(500, response.getStatus());
    }

    @Test
    public void createApplication_shouldReturn400_whenCreatingApplicationWithInvalidJobRole() {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "user@kainos.com",
                -1,
                "TestDocument.docx"
        );

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
                .target(System.getenv("API_URL")+"apply")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(applicationRequest));

        Assertions.assertEquals(400, response.getStatus());
    }
}
