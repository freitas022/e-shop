package com.meuportifolio.eshop.controllers;

import com.meuportifolio.eshop.dto.AuthRequest;
import com.meuportifolio.eshop.dto.UserDto;
import com.meuportifolio.eshop.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Authenticate user")
    @PostMapping(path = "/sign-in")
    public String signIn(@RequestBody @Valid AuthRequest dto) {
        return authService.signIn(dto);
    }

    @Operation(summary = "Create a new user")
    @PostMapping(path = "/sign-up")
    public void signUp(@RequestBody @Valid UserDto dto) {
        authService.signUp(dto);
    }
}
