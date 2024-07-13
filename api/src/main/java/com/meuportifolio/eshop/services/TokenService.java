package com.meuportifolio.eshop.services;

import com.meuportifolio.eshop.dto.UserDto;
import com.meuportifolio.eshop.entities.Role;
import com.meuportifolio.eshop.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserService userService;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UserService userService) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userService = userService;
    }

    public String generateToken(User user) {
        var claims = buildJwtClaims(user);
        return encodeJwtToken(claims);
    }

    private JwtClaimsSet buildJwtClaims(User user) {
        Instant now = Instant.now();

        String scopes = getScopes(user);

        return JwtClaimsSet.builder()
                .issuer("github.com/freitas022")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(2700L)) // 2700s = 45 MIN
                .claim("scope", scopes)
                .build();
    }

    private static String getScopes(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.joining(" "));
    }

    private String encodeJwtToken(JwtClaimsSet claims) {
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public void validateToken(String token, UserDto userDto) {
        var extractedToken = jwtDecoder.decode(token);
        var user = userService.findById(userDto.id());
        if (!user.id().toString().equals(extractedToken.getSubject())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
