package org.example.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.jsonwebtoken.Jwts;
import org.example.models.JwtToken;
import org.example.models.UserRole;

import java.security.Key;
import java.util.Optional;

public class JwtAuthenticator implements Authenticator<String, JwtToken> {
    Key key;

    public JwtAuthenticator(final Key key) {
        this.key = key;
    }

    @Override
    public Optional<JwtToken> authenticate(final String token) throws
            AuthenticationException {
        try {
            Integer roleId = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("Role", Integer.class);

            JwtToken jwtToken = new JwtToken(new UserRole(roleId));

            return Optional.of(jwtToken);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
