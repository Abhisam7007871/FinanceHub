package com.project_ledger.project_ledger.service;

import com.project_ledger.project_ledger.entity.AppUser;
import com.project_ledger.project_ledger.entity.User;
import com.project_ledger.project_ledger.repository.AppUserRepository;
import com.project_ledger.project_ledger.repository.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@ConditionalOnProperty(prefix = "spring.security.oauth2.client.registration", name = "google.client-id")
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AppUserRepository appUserRepository;
    private final UserRepository userRepository;

    public CustomOAuth2UserService(AppUserRepository appUserRepository, UserRepository userRepository) {
        this.appUserRepository = appUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauthUser = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attrs = oauthUser.getAttributes();
        
        // Extract provider-specific id/email/name safely
        String providerId = String.valueOf(attrs.get("sub") != null ? attrs.get("sub") : attrs.get("id"));
        String email = (String) attrs.getOrDefault("email", null);
        String name = (String) attrs.getOrDefault("name", attrs.getOrDefault("username", null));
        String picture = (String) attrs.getOrDefault("picture", null);

        // Upsert AppUser
        AppUser appUser = appUserRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> {
                    AppUser u = new AppUser();
                    u.setProvider(provider);
                    u.setProviderId(providerId);
                    return u;
                });

        appUser.setEmail(email);
        appUser.setName(name);
        appUser.setPictureUrl(picture);

        // Link to existing User entity or create new one
        if (appUser.getUser() == null && email != null) {
            User existingUser = userRepository.findByEmail(email).orElse(null);
            if (existingUser == null) {
                // Create new User for OAuth login
                existingUser = new User();
                existingUser.setEmail(email);
                existingUser.setName(name);
                existingUser.setPassword(""); // OAuth users don't have passwords
                existingUser.setEmailVerified(true);
                existingUser = userRepository.save(existingUser);
            }
            appUser.setUser(existingUser);
        }

        appUserRepository.save(appUser);
        return oauthUser;
    }
}