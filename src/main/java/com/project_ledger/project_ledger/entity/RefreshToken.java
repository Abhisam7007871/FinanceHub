package com.project_ledger.project_ledger.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;
    private String token;
    private Instant expiresAt;

    public RefreshToken() {}

    public RefreshToken(String id, String userId, String token, Instant expiresAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}