package org.example;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.example.controllers.FileImportController;
import org.example.controllers.JobRoleController;
import org.example.controllers.TestController;
import org.example.daos.FileImportDao;
import org.example.daos.JobRoleDao;
import org.example.daos.TestDao;
import org.example.services.BucketService;
import org.example.services.JobRoleService;
import io.jsonwebtoken.Jwts;
import org.example.auth.JwtAuthenticator;
import org.example.auth.RoleAuthoriser;
import org.example.controllers.AuthController;
import org.example.daos.AuthDao;
import org.example.models.JwtToken;
import org.example.services.AuthService;
import org.example.services.TestService;
import org.example.validators.FileImportValidator;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.example.validators.LoginValidator;

import java.security.Key;

public class TestApplication extends Application<TestConfiguration> {
    public static void main(final String[] args) throws Exception {

        new TestApplication().run(args);
    }
    @Override
    public String getName() {
        return "Test";
    }
    @Override
    public void initialize(final Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                    final TestConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }
    @Override
    public void run(final TestConfiguration configuration,
                    final Environment environment) {
        Key jwtKey = Jwts.SIG.HS256.key().build();
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<JwtToken>()
                        .setAuthenticator(new JwtAuthenticator(jwtKey))
                        .setAuthorizer(new RoleAuthoriser())
                        .setPrefix("Bearer")
                        .buildAuthFilter()
        ));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(
                JwtToken.class
        ));
        environment.jersey()
                .register(new TestController(new TestService(new TestDao())));
        environment.jersey().register(new FileImportController(new BucketService(new FileImportDao(), new FileImportValidator())));
        environment.jersey()
                .register(new JobRoleController(
                            new JobRoleService(
                                new JobRoleDao())));
        environment.jersey()
            .register(new AuthController(new AuthService(new AuthDao(),
                        new LoginValidator(),
                        jwtKey)));
    }

}
