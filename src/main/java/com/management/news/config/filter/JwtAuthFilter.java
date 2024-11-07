package com.management.news.config.filter;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.management.news.service.JwtService;
import com.management.news.service.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtService JwtService;

    @Autowired
    private UserInfoService UserInfoService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        var securityContext = SecurityContextHolder.getContext();

        if (authHeader != null && authHeader.startsWith("Bearer"))
        {
            token = authHeader.substring(7);
            username = JwtService.ExtractUsername(token);
        }

        if (username != null && securityContext.getAuthentication() == null)
        {
            var userDetails = UserInfoService.loadUserByUsername(username);
            if (JwtService.ValidateToken(token, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities());

                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }
}
