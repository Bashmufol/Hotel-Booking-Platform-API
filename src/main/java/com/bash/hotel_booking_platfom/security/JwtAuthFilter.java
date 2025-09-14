package com.bash.hotel_booking_platfom.security;

import com.bash.hotel_booking_platfom.service.CustomUserDetailsService;
import com.bash.hotel_booking_platfom.service.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    // Check if the request contains a valid Authorization header with a JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String bearer = "Bearer ";

        // Proceed only if the user is not already authenticated
        if(authHeader != null && authHeader.startsWith(bearer)) {
            final String jwt = authHeader.substring(bearer.length());
            final String username = jwtService.extractUsername(jwt);

            // Proceed only if the user is not already authenticated
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserPrincipal userPrincipal = (UserPrincipal) this.customUserDetailsService.loadUserByUsername(username);

                // Validate token before setting authentication in the security context
                if(jwtService.isTokenValid(jwt, userPrincipal)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userPrincipal, null, userPrincipal.getAuthorities()
                    );

                    // Attach request-specific details (IP, session, etc.)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Store authentication so Spring Security knows the user is logged in
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // Continue the filter chain regardless of authentication outcome
        filterChain.doFilter(request, response);
    }
}
