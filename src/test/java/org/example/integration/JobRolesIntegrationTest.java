package org.example.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.example.TestApplication;
import org.example.TestConfiguration;
import org.example.models.JobRoleResponse;
import org.example.models.LoginRequest;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JobRolesIntegrationTest {
    static final DropwizardAppExtension<TestConfiguration> APP = new DropwizardAppExtension<>(
            TestApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getJobRoleById_shouldReturnAJobRole() {

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
                .target(System.getenv("API_URL")+"job-roles/2")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void get10JobRoles_shouldReturn10JobRoles() {

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

        System.out.println(response);

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void get25JobRoles_shouldReturn25JobRoles() {

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
                .target(System.getenv("API_URL")+"job-roles/?page=1&limit=25")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get();

        System.out.println(response);

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void get50JobRoles_shouldReturn50JobRoles() {

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
                .target(System.getenv("API_URL")+"job-roles/?page=1&limit=50")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get();

        System.out.println(response);

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void get100JobRoles_shouldReturn100JobRoles() {

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
                .target(System.getenv("API_URL")+"job-roles/?page=1&limit=100")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get();

        System.out.println(response);

        Assertions.assertEquals(200, response.getStatus());

    }


    @Test
    void getJobRolesReport_shouldReturnCsvFile() {

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.READ_TIMEOUT, 60000); // 60 seconds
        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 60000); // 60 seconds
        Client client = ClientBuilder.newClient(clientConfig);

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
                .target(System.getenv("API_URL")+"job-roles/report")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get();

        System.out.println(response);

        Assertions.assertEquals(200, response.getStatus());

    }

}
