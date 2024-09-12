package org.example.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.example.TestApplication;
import org.example.TestConfiguration;
import org.example.models.LoginRequest;
import org.example.models.ProfileRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ProfileIntegrationTest {
    static final DropwizardAppExtension<TestConfiguration> APP = new DropwizardAppExtension<>(
            TestApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void updateProfile_shouldReturn204_whenSuccessfulUpdate() {
        Client client = APP.client();

        ProfileRequest profileRequest = new ProfileRequest(
                "user@kainos.com",
                null
        );

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
                .target(System.getenv("API_URL")+"profile")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .put(Entity.json(profileRequest));

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void updateProfile_shouldReturn401_whenUnauthorised() {
        Client client = APP.client();

        ProfileRequest profileRequest = new ProfileRequest(
                "user@kainos.com",
                null
        );


        Response response = client
                .target(System.getenv("API_URL")+"profile")
                .request()
                .put(Entity.json(profileRequest));

        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    void getProfile_shouldReturn200_whenSuccessfulRequest() {
        Client client = APP.client();

        String email = "user@kainos.com";

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
                .target(System.getenv("API_URL")+"profile")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(email));

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void getProfile_shouldReturn404_whenEmailDoesNotExist() {
        Client client = APP.client();

        String email = "wrong@kainos.com";

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
                .target(System.getenv("API_URL")+"profile")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(email));

        Assertions.assertEquals(404, response.getStatus());
    }
}
