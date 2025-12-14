package com.example.demo.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("--- Processing Request: " + request.getRequestURI() + " ---");

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    System.out.println("1. Cookie found! Token: " + token);
                }
            }
        } else {
            System.out.println("1. No cookies found in request.");
        }

        if (token != null) {
            try {
                String username = jwtService.extractUsername(token);
                String role = jwtService.extractRole(token);

                System.out.println("2. Extracted Username: " + username);
                System.out.println("3. Extracted Role: " + role);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Construct the authorities carefully
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    authorities
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("4. Authentication set in SecurityContext for: " + username);
                }
            } catch (Exception e) {
                System.out.println("Error validating token: " + e.getMessage());
                e.printStackTrace(); // This will print the actual error if the token is wrong
            }
        } else {
            System.out.println("Token was null, skipping authentication.");
        }

        filterChain.doFilter(request, response);
    }
}