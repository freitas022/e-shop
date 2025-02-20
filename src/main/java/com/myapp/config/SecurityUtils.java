package com.myapp.config;

import com.myapp.dtos.CustomUserDto;
import com.myapp.repositories.UserRepository;
import com.myapp.services.exceptions.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository userRepository;

    /**
     * Get the authenticated user email
     *
     * @return The authenticated user email.
     */
    public CustomUserDto getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            return userRepository.findByEmail(authentication.getName())
                    .map(u -> new CustomUserDto(u.getId(), u.getEmail()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new UnauthorizedAccessException("Forbidden access", e.getCause());
        }
    }
}
