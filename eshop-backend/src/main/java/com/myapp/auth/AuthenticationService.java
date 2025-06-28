package com.myapp.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthResponseDto authenticate(AuthRequestDto request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        
        String username = authentication.getName();

        String accessToken = jwtTokenProvider.generateToken(username);

        var refreshToken = refreshTokenService.createRefreshToken(username);

        return new AuthResponseDto(accessToken, refreshToken);
    }

    public String refreshToken(String refreshToken) {
        var tokenOpt = refreshTokenService.getRefreshToken(refreshToken);

        if (!refreshTokenService.isValid(tokenOpt)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
        }
        String username = refreshTokenService.getUsername(tokenOpt);
        return jwtTokenProvider.generateToken(username);
    }

    @Transactional
    public void logout(String refreshToken) {
       refreshTokenService.delete(refreshToken);
    }
}
