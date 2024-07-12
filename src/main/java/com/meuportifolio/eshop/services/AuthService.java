package com.meuportifolio.eshop.services;

import com.meuportifolio.eshop.dto.AuthRequest;
import com.meuportifolio.eshop.repositories.UserRepository;
import com.meuportifolio.eshop.services.exceptions.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, TokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public String signIn(AuthRequest authRequest) {
        var user = userRepository.findByEmail(authRequest.email());

        isSamePassword(user.getPassword(), authRequest.password());

        return tokenService.generateToken(user);
    }

    private void isSamePassword(String hashedPassword, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            throw new BadCredentialsException("Email or password incorrect.");
        }
    }
}
