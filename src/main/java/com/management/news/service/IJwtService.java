package com.management.news.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String GenerateToken(String username);
    boolean ValidateToken(String token, UserDetails user);
    String ExtractUsername(String token);
}
