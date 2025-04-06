package com.accesscontrol.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret:MySuperSecureJWTSecretKeyThatIsLongEnough123!}") // temporary
    private String jwtSecret;
   
    private final long EXPIRATION_MS = 86400000;

    public String generateToken(String username){
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)).
        signWith(key).compact();
    }
    
    public String extractUsername(String token){
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }



}
