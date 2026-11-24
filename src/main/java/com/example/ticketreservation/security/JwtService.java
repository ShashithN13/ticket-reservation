package com.example.ticketreservation.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class JwtService {
    @Value("${app.jwt.secret}") private String secret;
    @Value("${app.jwt.expiration-ms}") private long expirationMs;
    private SecretKey key() { return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); }
    public String generateToken(String username) {
        Date now=new Date();
        return Jwts.builder().setSubject(username).setIssuedAt(now)
          .setExpiration(new Date(now.getTime()+expirationMs)).signWith(key(), SignatureAlgorithm.HS384).compact();
    }
    public String extractUsername(String token) { return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject(); }
    public boolean isValid(String token) {
        try { Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token); return true; }
        catch (JwtException | IllegalArgumentException ex) { return false; }
    }
    public long getExpirationMs() { return expirationMs; }
}
