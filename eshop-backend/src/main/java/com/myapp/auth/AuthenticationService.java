package com.myapp.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myapp.exceptions.InvalidCredentialsException;
import com.myapp.user.UserRequestDto;
import com.myapp.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public String signIn(AuthRequestDto request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            log.info("Attempting login for user: {}", request.email());
            return jwtTokenProvider.generateToken(authentication.getName());
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }

    public void signUp(UserRequestDto request) throws JsonProcessingException {
        log.info("New user registration attempt: {}", request);
        userService.insert(request);
    }

    public String getUserInfo(Principal principal) {
        return principal != null ? "User authenticated: " + principal.getName()
                : "No user authenticated";
    }
}
