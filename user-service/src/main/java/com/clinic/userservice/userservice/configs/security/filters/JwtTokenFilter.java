package com.clinic.userservice.userservice.configs.security.filters;

import com.clinic.commonservice.exceptions.TokenException;
import com.clinic.commonservice.logs.WriteLog;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.List;

/**
 * Filter for validating JWT tokens in incoming requests.
 * Extends OncePerRequestFilter to ensure single execution per request.
 * Handles extraction and validation of JWT tokens from the Authorization header.
 * Sets the authentication in the SecurityContext if the token is valid.
 * Logs errors and throws TokenException for invalid tokens.
 * Uses a predefined secret key for token validation.
 *
 * @author : caito
 *
 */
@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String SECRET ="Hw9z1Yk8Nmq1IzlwcCg8j6yHzw6RKjzZUi9r7Ww555o0PP";

    /**
     * Filters incoming requests to validate JWT tokens.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
            token = header.substring(7);
        if (token != null) {
            Claims claims = null;
            try {
                claims = Jwts.parser()
                        .setSigningKey(getKey())
                        .parseClaimsJws(token)
                        .getBody();
            }catch (Exception e) {
                log.error(WriteLog.logError("Error parsing JWT token: " + e.getMessage()));
                throw new TokenException("Invalid JWT token" + e.getMessage());
            }
            String userName = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userName,
                    null,
                    roles.stream().map(SimpleGrantedAuthority::new).toList());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);

    }

    /**
     * Retrieves the signing key for JWT token validation.
     *
     * @return the signing key
     */
    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
}
