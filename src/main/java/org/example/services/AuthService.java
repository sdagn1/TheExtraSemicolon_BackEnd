package org.example.services;

import io.jsonwebtoken.Jwts;
import org.example.daos.AuthDao;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.LoginRequest;
import org.example.models.User;
import org.example.validators.LoginValidator;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

public class AuthService {
    private final AuthDao authDao;
    private final LoginValidator loginValidator;
    private final Key key;
    private final int tokenDuration = 28800000;
    private final int saltSize = 128 * 6;
    private final int hashSize = 128 * 6;
    private final int hashIterations = 65536;

    public AuthService(final AuthDao authDao,
                       final LoginValidator loginValidator,
                       final Key key) {
        this.authDao = authDao;
        this.loginValidator = loginValidator;
        this.key = key;
    }

    public String login(final LoginRequest loginRequest)
            throws SQLException, InvalidException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        loginValidator.validateLogin(loginRequest);
        User user = authDao.getUser(loginRequest);

        if (user == null) {
            throw new InvalidException(Entity.USER, "Invalid credentials");
        }
        String hashedPassword = generatePBKDF2Hash(
                loginRequest.getPassword(), user.getSalt());
        User validateUser = authDao.validateUser(
                new LoginRequest(loginRequest.getEmail(), hashedPassword));

        if (validateUser == null) {
            throw new InvalidException(Entity.USER, "Invalid credentials");
        }
        return generateJwtToken(validateUser);
    }

    private String generateJwtToken(final User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()
                        + tokenDuration))
                .claim("Role", user.getRoleId())
                .subject(user.getEmail())
                .issuer("TheMissingSemicolon")
                .signWith(key)
                .compact();
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltSize];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String generatePBKDF2Hash(final String password, final String salt)
    throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(),
                salt.getBytes(), hashIterations, hashSize);

        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

}

