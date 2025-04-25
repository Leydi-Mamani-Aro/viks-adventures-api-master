package com.upc.viksadventuresapi.iam.infrastructure.authorization.sfs.pipeline;

import com.upc.viksadventuresapi.iam.infrastructure.tokens.jwt.BearerTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Bearer Authorization Request Filter.
 * Sets user authentication based on a valid JWT.
 */
public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BearerAuthorizationRequestFilter.class);
    private final BearerTokenService tokenService;

    @Qualifier("defaultUserDetailsService")
    private final UserDetailsService userDetailsService;

    public BearerAuthorizationRequestFilter(BearerTokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            LOGGER.info("🔐 Iniciando filtro de autorización Bearer...");

            String token = tokenService.getBearerTokenFrom(request);
            LOGGER.info("📥 Token recibido en cabecera: {}", token != null ? token : "null");

            if (token != null && tokenService.validateToken(token)) {
                Jwt jwt = tokenService.parseToken(token); // Devuelve objeto Jwt
                LOGGER.info("✅ JWT decodificado correctamente.");

                String username = jwt.getSubject();  // <- CORREGIDO
                LOGGER.info("👤 Username extraído (sub): {}", username != null ? username : "null");

                if (username == null || username.isBlank()) {
                    LOGGER.warn("⚠️ El token es válido pero no contiene un 'subject' (username).");
                } else {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    LOGGER.info("✅ UserDetails cargado correctamente: {}", userDetails.getUsername());

                    JwtAuthenticationToken authentication =
                            new JwtAuthenticationToken(jwt, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    LOGGER.info("🔓 Autenticación establecida en SecurityContext para: {}", username);
                }

            } else {
                LOGGER.warn("❌ Token inválido o no presente.");
            }

        } catch (Exception e) {
            LOGGER.error("❌ Error al procesar autenticación: {}", e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }
}