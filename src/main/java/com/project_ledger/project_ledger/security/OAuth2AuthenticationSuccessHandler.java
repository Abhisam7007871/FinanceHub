package com.project_ledger.project_ledger.security;

import com.project_ledger.project_ledger.entity.AppUser;
import com.project_ledger.project_ledger.entity.RefreshToken;
import com.project_ledger.project_ledger.repository.AppUserRepository;
import com.project_ledger.project_ledger.repository.RefreshTokenRepository;
import com.project_ledger.project_ledger.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
@ConditionalOnProperty(prefix = "spring.security.oauth2.client.registration", name = "google.client-id")
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final String frontendUrl;

    public OAuth2AuthenticationSuccessHandler(AppUserRepository appUserRepository,
                                              JwtService jwtService,
                                              RefreshTokenRepository refreshTokenRepository,
                                              @Value("${cors.allowed-origins:http://localhost:3000}") String allowedOrigins) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.frontendUrl = allowedOrigins.contains(",") ? allowedOrigins.split(",")[0] : allowedOrigins;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String provider = ((org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken) authentication)
                .getAuthorizedClientRegistrationId();
        String providerId = String.valueOf(oauth2User.getAttribute("sub") != null ? 
                oauth2User.getAttribute("sub") : oauth2User.getAttribute("id"));

        AppUser user = appUserRepository.findByProviderAndProviderId(provider, providerId)
                .orElseThrow(() -> new RuntimeException("User not found after OAuth2 login"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Persist refresh token
        RefreshToken rt = new RefreshToken();
        rt.setUserId(user.getId());
        rt.setToken(refreshToken);
        rt.setExpiresAt(Instant.now().plusSeconds(60L*60*24*30));
        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(rt);

        // Set HttpOnly Secure cookies
        Cookie accessCookie = new Cookie("ACCESS_TOKEN", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false); // Set to true in production with HTTPS
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60*15); // 15min

        Cookie refreshCookie = new Cookie("REFRESH_TOKEN", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false); // Set to true in production with HTTPS
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(60*60*24*30); // 30 days

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        // Redirect to dashboard after successful OAuth2 login
        response.sendRedirect("/dashboard");
    }
}