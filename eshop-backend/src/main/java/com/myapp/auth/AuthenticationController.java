package com.myapp.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myapp.user.UserDto;
import com.myapp.user.UserRequestDto;
import com.myapp.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authService;
    private final UserService userService;

    @Operation(summary = "Authenticate user")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDto request, HttpServletResponse response) {
        var accessToken = authService.signIn(request);

        var cookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge((365 * 24 * 60 * 60) * 5) // 157.680.000 seconds = 5 years
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Register new user")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequestDto request) throws JsonProcessingException {
        authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get info from authenticated user")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(user);
    }
}
