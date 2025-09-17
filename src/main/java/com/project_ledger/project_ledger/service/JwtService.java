package com.project_ledger.project_ledger.service;

import com.project_ledger.project_ledger.entity.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key key;
    private final long accessTokenMillis;
    private final long refreshTokenMillis;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.access-token-expiration}") long accessTokenMillis,
                      @Value("${jwt.refresh-token-expiration}") long refreshTokenMillis) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenMillis = accessTokenMillis;
        this.refreshTokenMillis = refreshTokenMillis;
    }

    public String generateAccessToken(AppUser user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuer("project-ledger")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(accessTokenMillis)))
                .addClaims(Map.of(
                     "email", user.getEmail() != null ? user.getEmail() : "",
                     "name", user.getName() != null ? user.getName() : "",
                     "provider", user.getProvider()
                ))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(AppUser user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuer("project-ledger")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(refreshTokenMillis)))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}