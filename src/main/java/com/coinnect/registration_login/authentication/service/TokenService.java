package com.coinnect.registration_login.authentication.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.Base64;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.coinnect.registration_login.authentication.dto.LoginRequestDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

    private final String SECRET_KEY = "QWhJZ28ZkXv4MlZ1P1kZzX7fSLmy58D7Oj68jk9rGcM="; 
    private final long expirationTime = 1000 * 60 * 60 * 24;  

    public String login(LoginRequestDTO loginRequestDTO, UserDetails userDetails) {
        if (userDetails == null || !userDetails.getUsername().equals(loginRequestDTO.getUserName())) {
            throw new RuntimeException("Invalid username or password");
        }

        return getToken(new HashMap<>(), userDetails);
    }

    public String getToken(UserDetails user){
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)  
                .setSubject(user.getUsername())  
                .setIssuedAt(new Date(System.currentTimeMillis()))  
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) 
                .signWith(getKey(), SignatureAlgorithm.HS256)  
                .compact();  
    }

    private Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);  
        return Keys.hmacShaKeyFor(keyBytes);  
    }

    public String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        return Jwts.parser()
                   .setSigningKey(SECRET_KEY)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
