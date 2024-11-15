package com.coinnect.registration_login.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

    private final String SECRET_KEY = "QWhJZ28ZkXv4MlZ1P1kZzX7fSLmy58D7Oj68jk9rGcM="; 
    private final long expirationTime = 1000 * 60 * 60 * 24;  

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)  // Si hay claims extra, se pasan aquí
                .setSubject(user.getUsername())  // El nombre de usuario es el "subject"
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Fecha de expiración
                .signWith(getKey(), SignatureAlgorithm.HS256)  // Firma usando la clave secreta
                .compact();  // Genera el JWT
    }

    private Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);  
        return Keys.hmacShaKeyFor(keyBytes);  
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);  
    }

    private <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token); 
        return claimsResolver.resolve(claims); 
    }

    private Claims extractAllClaims(String token) {
        return Jwts.builder()  
                .setSigningKey(SECRET_KEY) 
                .build() 
                .parseClaimsJws(token)  
                .getBody();  
    }

    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
