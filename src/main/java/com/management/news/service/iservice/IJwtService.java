package com.management.news.service.iservice;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(String username);
    boolean validateToken(String token, UserDetails user);
    String extractUsername(String token);
}
