package com.project_ledger.project_ledger.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user", uniqueConstraints = @UniqueConstraint(columnNames = {"provider", "provider_id"}))
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String provider; // "google", "facebook", "apple", "local"

    @Column(name = "provider_id", nullable = false)
    private String providerId; // sub / id from provider

    @Column(nullable = true)
    private String email;

    private String name;
    private String pictureUrl;
    private String phone;
    
    // Link to existing User entity for backward compatibility
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public AppUser() {}

    public AppUser(String id, String provider, String providerId, String email, String name, String pictureUrl, String phone, User user) {
        this.id = id;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.phone = phone;
        this.user = user;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPictureUrl() { return pictureUrl; }
    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}