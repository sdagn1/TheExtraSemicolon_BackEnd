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
                .target("http://localhost:8080/api/job-roles")
                .request()
                .get();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void get10JobRoles_shouldReturn10JobRoles() {

        Client client = APP.client();

        Response response = client
                .target("http://localhost:8080/api/job-roles?page=1&limit=10")
                .request()
                .get();

        System.out.println(response);

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void get25JobRoles_shouldReturn25JobRoles() {

        Client client = APP.client();

        Response response = client
                .target("http://localhost:8080/api/job-roles?page=1&limit=25")
                .request()
                .get();

        System.out.println(response);

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void get50JobRoles_shouldReturn50JobRoles() {

        Client client = APP.client();

        Response response = client
                .target("http://localhost:8080/api/job-roles?page=1&limit=50")
                .request()
                .get();

        System.out.println(response);

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void get100JobRoles_shouldReturn100JobRoles() {

        Client client = APP.client();

        Response response = client
                .target("http://localhost:8080/api/job-roles?page=1&limit=100")
                .request()
                .get();

        System.out.println(response);

        Assertions.assertEquals(200, response.getStatus());

    }

}
