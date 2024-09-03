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
    void getAllJobRoles_shouldReturn401Error_whenUnauthorized() {

        Client client = APP.client();

        Response response = client
                .target("http://localhost:8080/api/job-roles")
                .request()
                .get();

        Assertions.assertEquals(401, response.getStatus());

    }
}
