package com.myapp.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myapp.user.UserDto;
import com.myapp.user.UserRequestDto;
import com.myapp.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final CookieUtils cookieUtils;

    @Operation(summary = "Authenticate user and set cookies")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthRequestDto request, HttpServletResponse response) {
        var authResponse = authenticationService.authenticate(request);

        ResponseCookie accessTokenCookie = cookieUtils.createCookie("accessToken", authResponse.accessToken(), 3600);
        ResponseCookie refreshTokenCookie = cookieUtils.createCookie("refreshToken", authResponse.refreshToken(), 365 * 24 * 3600);

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Logout user and clear cookies")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        cookieUtils.getCookieValue(request, "refreshToken").ifPresent(authenticationService::logout);

        ResponseCookie deleteAccessToken = cookieUtils.deleteCookie("accessToken");
        ResponseCookie deleteRefreshToken = cookieUtils.deleteCookie("refreshToken");

        response.addHeader(HttpHeaders.SET_COOKIE, deleteAccessToken.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = cookieUtils.getCookieValue(request, "refreshToken");

        if (refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            String newAccessToken = authenticationService.refreshToken(refreshToken.get());
            ResponseCookie newAccessTokenCookie = cookieUtils.createCookie("accessToken", newAccessToken, 3600);
            response.addHeader(HttpHeaders.SET_COOKIE, newAccessTokenCookie.toString());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Create a new user account")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRequestDto dto) throws JsonProcessingException {
        userService.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get info about the authenticated user")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        UserDto user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }
}
