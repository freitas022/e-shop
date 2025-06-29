package com.myapp.auth;

import com.myapp.exceptions.InvalidCredentialsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthResponseDto authenticate(AuthRequestDto request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));

            String username = authentication.getName();
            log.info("Successful authentication for user: {}", username);

            String accessToken = jwtTokenProvider.generateToken(username);
            var refreshToken = refreshTokenService.createRefreshToken(username);

            return new AuthResponseDto(accessToken, refreshToken);

        } catch (BadCredentialsException e) {
            log.warn("Authentication failed: Invalid credentials for email {}", request.email());
            throw new InvalidCredentialsException();
        } catch (UsernameNotFoundException e) {
            log.warn("Authentication failed: User not found for email {}", request.email());
            throw new InvalidCredentialsException();
        } catch (AuthenticationException e) {
            log.error("Error during authentication for email {}: {}", request.email(), e.getMessage(), e);
            throw new InvalidCredentialsException("Error authenticating. Please try again.");
        } catch (Exception e) {
            log.error("Unexpected error during authentication for email {}: {}", request.email(), e.getMessage(), e);
            throw new RuntimeException("Internal error during authentication", e);
        }
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
