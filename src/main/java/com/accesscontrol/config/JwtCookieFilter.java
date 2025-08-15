package com.accesscontrol.config;

import com.accesscontrol.models.CustomUserDetails;
import com.accesscontrol.services.CustomUserDetailsService;
import com.accesscontrol.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtCookieFilter extends OncePerRequestFilter {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private CustomUserDetailsService customUserDetailsService;

    // IMPORTANT: must match your Set-Cookie name from login/logout controllers
    private static final String COOKIE_NAME = "token"; // or "token", but be consistent

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractTokenFromCookies(request);

        // If no token or blank -> proceed unauthenticated
        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Only set auth if not already set
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtUtil.extractUsername(token); // may throw if invalid/expired

                if (username != null && jwtUtil.isTokenValid(token, username)) {
                    CustomUserDetails userDetails =
                        (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception ex) {
            // Do NOT fail the request — just continue unauthenticated
            // Optionally log: logger.debug("JWT parse/validation failed", ex);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    // Optional: skip filter for public/auth endpoints
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String p = request.getRequestURI();
        return p.startsWith("/api/auth/login")
            || p.startsWith("/api/auth/logout")
            || p.startsWith("/api/public/");
    }
}
