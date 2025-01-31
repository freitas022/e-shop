package com.myapp.resources;

import com.myapp.dtos.AuthRequestDto;
import com.myapp.services.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        log.info("Attempting login for user: {}", authRequestDto.email());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.email(), authRequestDto.password())
        );

        log.info("Login successful for user: {}", authentication.getName());
        var accessToken = jwtTokenProvider.generateToken(authentication.getName());

        return ResponseEntity.ok().body(accessToken);
    }

    @GetMapping("/user-info")
    public String getUserInfo(Principal principal) {
        if (principal != null) {
            return "Usuário logado: " + principal.getName();
        }
        return "Nenhum usuário logado";
    }
}
