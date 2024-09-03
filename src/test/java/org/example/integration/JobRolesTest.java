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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JobRolesTest {
    static final DropwizardAppExtension<TestConfiguration> APP = new DropwizardAppExtension<>(
            TestApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getAllJobRoles_shouldReturnAllJobRoles() {

        List<String> locations = new ArrayList<>();
        locations.add("Atlanta");
        locations.add("Amsterdam");
        locations.add("Belfast");

        Date date = new Date();

        JobRoleResponse jobRoleResponse = new JobRoleResponse(
                1,
                "Technology Leader",
                locations,
                "Engineering",
                "Associate",
                date
        );

        Client client = APP.client();

        Response response = client
                .target("http://localhost:8080/api/job-roles")
                .request()
                .get();

        Assertions.assertEquals(200, response.getStatus());

    }

}
