package com.myapp.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    public String generateToken(String username) {
        log.info("Generating token for user: {}", username);
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            Instant now = Instant.now();
            Instant expirationTime = now.plus(Duration.ofHours(1));

            String accessToken = JWT.create()
                    .withSubject(username)
                    .withIssuer("spring-boot-starter-oauth2-resource-server")
                    .withIssuedAt(now)
                    .withExpiresAt(expirationTime)
                    .withJWTId(UUID.randomUUID().toString())
                    .sign(algorithm);

            log.info("Token generated successfully for user: {}", username);
            return accessToken;
        } catch (JWTCreationException e) {
            log.error("Error generating token for user {}: {}", username, e.getMessage());
            throw new JWTGenerationException("Failed to generate JWT token", e);
        }
    }

    public Optional<String> validateToken(String token) {
        if (token == null || token.isEmpty()) {
            log.warn("Token is null or empty");
            return Optional.empty();
        }

        log.debug("Attempting to validate token: {}", token);

        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            String subject = JWT.require(algorithm)
                    .withIssuer("spring-boot-starter-oauth2-resource-server")
                    .build()
                    .verify(token)
                    .getSubject();
            log.info("Token validated successfully");
            return Optional.ofNullable(subject);
        } catch (JWTDecodeException decodeException) {
            log.error("Failed to decode JWT token", decodeException);
            return Optional.empty();
        } catch (JWTVerificationException exception) {
            log.warn("Invalid token", exception);
            return Optional.empty();
        }
    }



    public Optional<String> getUsernameFromToken(String token) {
        log.info("Getting username from token: {}", token);
        if (token == null || token.isEmpty()) {
            log.warn("Token is null or empty");
            return Optional.empty();
        }

        try {
            DecodedJWT jwt = JWT.decode(token);
            String username = jwt.getSubject();

            log.info("Username extracted from token: {}", username);
            return Optional.of(username);

        } catch (JWTDecodeException e) {
            log.error("Error decoding token: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
