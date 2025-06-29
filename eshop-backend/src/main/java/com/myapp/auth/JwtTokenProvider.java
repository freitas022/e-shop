package com.myapp.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.myapp.exceptions.JWTGenerationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class JwtTokenProvider {

    @Value("${api.secret.key}")
    private String secretKey;

    private static final Duration ACCESS_TOKEN_VALIDITY = Duration.ofHours(1);
    private static final String ISSUER = "E-shop.dev";

    /**
     * Generates a JWT token for the provided user.
     *
     * @param username Name of the user for which the token will be generated.
     * @return Token JWT generated.
     * @throws JWTGenerationException If an error occurs during token generation.
     */
    public String generateToken(String username) {
        log.info("Generating token for user: {}", username);
        try {
            Instant now = Instant.now();
            Instant expirationTime = now.plus(ACCESS_TOKEN_VALIDITY);
            Algorithm algorithm = Algorithm.HMAC512(secretKey);

            return JWT.create()
                    .withSubject(username)
                    .withIssuer(ISSUER)
                    .withIssuedAt(now)
                    .withExpiresAt(expirationTime)
                    .withJWTId(UUID.randomUUID().toString())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            log.error("Error generating token for user {}: {}", username, e.getMessage(), e);
            throw new JWTGenerationException("Failed to generate JWT token for user: " + username, e);
        }
    }

    /**
     * Validates a JWT token and returns the Subject (username) if valid.
     *
     * @param token Token JWT to be validated.
     * @return Optional containing Subject (username) if the token is valid, or empty otherwise.
     */
    public Optional<String> validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            log.warn("Token validation failed: token is null or empty");
            return Optional.empty();
        }

        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            String subject = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
            return Optional.ofNullable(subject);
        } catch (JWTVerificationException e) {
            log.warn("Token validation failed: invalid or expired token - {}", e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            log.error("Unexpected error during token validation: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
