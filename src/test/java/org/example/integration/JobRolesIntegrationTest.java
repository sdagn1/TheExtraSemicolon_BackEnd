package org.example.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.example.TestApplication;
import org.example.TestConfiguration;
import org.example.models.JobRoleResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Client;
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
    void getAllJobRoles_shouldReturnAllJobRoles() {

        Client client = APP.client();

        Response response = client
                .target(System.getenv("API_URL")+"job-roles")
                .request()
                .get();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void getJobRoleById_shouldReturnAJobRole() {

        Client client = APP.client();

        Response response = client
                .target("http://localhost:8080/api/job-roles/2")
                .request()
                .get();

        Assertions.assertEquals(200, response.getStatus());

    }

}
