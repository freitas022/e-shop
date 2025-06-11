package com.myapp.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myapp.exceptions.InvalidCredentialsException;
import com.myapp.user.UserRequestDto;
import com.myapp.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Operation(summary = "Authenticate user")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        log.info("Attempting login for user: {}", authRequestDto.email());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.email(), authRequestDto.password())
            );

            log.info("Login successful for user: {}", authentication.getName());
            var accessToken = jwtTokenProvider.generateToken(authentication.getName());
            return ResponseEntity.ok().body(accessToken);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Operation(summary = "Register new user")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequestDto userRequestDto) throws JsonProcessingException {
        log.info("New user registration attempt: {}", userRequestDto);
        userService.insert(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get info from authenticated user")
    @GetMapping("/user-info")
    public String getUserInfo(Principal principal) {
        if (principal != null) {
            return "Usuário logado: " + principal.getName();
        }
        return "Nenhum usuário logado";
    }
}
