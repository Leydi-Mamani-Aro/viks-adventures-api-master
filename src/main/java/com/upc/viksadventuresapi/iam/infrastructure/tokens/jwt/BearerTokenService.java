package com.upc.viksadventuresapi.iam.infrastructure.tokens.jwt;

import com.upc.viksadventuresapi.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * This interface is a marker interface for the JWT token service.
 * It extends the {@link TokenService} interface.
 * This interface is used to inject the JWT token service in the {@link com.upc.viksadventuresapi.iam.infrastructure.tokens.jwt.services} class.
 */
public interface BearerTokenService extends TokenService {

    /**
     * Extracts the JWT token from the HTTP request.
     * @param request the HTTP request
     * @return String the JWT token
     */
    String getBearerTokenFrom(HttpServletRequest request);

    /**
     * Generates a JWT token from an Authentication object.
     * @param authentication the authentication object
     * @return String the JWT token
     */
    String generateToken(Authentication authentication);

    /**
     * Parses and validates the JWT token and returns a Jwt object.
     * Allows access to claims like userId, roles, etc.
     * @param token the raw JWT string
     * @return Jwt the parsed and validated JWT object
     */
    Jwt parseToken(String token);
}
