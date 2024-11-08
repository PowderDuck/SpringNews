package com.management.news.service;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService implements IJwtService {
    private static final String KEY = "AV15236AADVARB736251";
    private static final long EXPIRATION_TIME = 1000L * 60L * 10L; // Valid for 10 minutes;

    public String generateToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public String extractUsername(String token)
    {
        return extractClaims(token).getSubject();
    }

    private Date extractExpirationDate(String token)
    {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isExpired(Date expirationDate)
    {
        return expirationDate.before(new Date());
    }

    public boolean validateToken(String token, UserDetails user)
    {
        return extractUsername(token).equals(user.getUsername()) 
            && !isExpired(extractExpirationDate(token));
    }
}
