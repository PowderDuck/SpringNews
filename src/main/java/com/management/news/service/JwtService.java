package com.management.news.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService implements IJwtService {
    private static final String KEY = "AV15236AADVARB736251";
    private static final long EXPIRATION_TIME = 1000L * 60L * 10L; // Valid for 10 minutes;

    public String GenerateToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public String ExtractUsername(String token)
    {
        return ExtractClaims(token).getSubject();
    }

    private Date ExtractExpirationDate(String token)
    {
        return ExtractClaims(token).getExpiration();
    }

    private Claims ExtractClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean IsExpired(Date expirationDate)
    {
        return expirationDate.before(new Date());
    }

    public boolean ValidateToken(String token, UserDetails user)
    {
        return ExtractUsername(token).equals(user.getUsername()) 
            && !IsExpired(ExtractExpirationDate(token));
    }
}
