package com.coinnect.registration_login.Jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    // private final String SECRET_KEY = "secretKey12345"; 
    // private final long EXPIRATION_TIME = 86400000;

    // public String generateToken(String username, Long userId){
    //     return Jwts.builder()
    //             .setSubject(username)
    //             .claim("userId", userId)
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
    //             .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
    //             .compact();
    // }

    // public Claims validateToken(String token) {
    //     return Jwts.parser()
    //             .setSigningKey(SECRET_KEY)
    //             .parseClaimsJws(token)
    //             .getBody();
    // }
}
