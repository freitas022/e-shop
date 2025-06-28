package com.myapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public String createRefreshToken(String username) {
        String refreshToken = UUID.randomUUID().toString();
        Instant expiry = Instant.now().plus(Duration.ofDays(365));
        refreshTokenRepository.save(new RefreshToken(refreshToken, username, expiry));
        return refreshToken;
    }

    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token not found"));
    }

    public boolean isValid(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isAfter(Instant.now());
    }

    public String getUsername(RefreshToken refreshToken) {
        return refreshToken.getUsername();
    }

    public void delete(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
