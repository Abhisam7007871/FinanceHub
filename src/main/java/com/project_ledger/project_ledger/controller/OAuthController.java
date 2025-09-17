package com.project_ledger.project_ledger.controller;

import com.project_ledger.project_ledger.entity.AppUser;
import com.project_ledger.project_ledger.repository.AppUserRepository;
import com.project_ledger.project_ledger.repository.RefreshTokenRepository;
import com.project_ledger.project_ledger.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserRepository appUserRepository;

    public OAuthController(JwtService jwtService,
                          RefreshTokenRepository refreshTokenRepository,
                          AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.setStatus(401); 
            return;
        }
        
        String refreshToken = null;
        for (Cookie c : cookies) {
            if ("REFRESH_TOKEN".equals(c.getName())) {
                refreshToken = c.getValue();
                break;
            }
        }
        
        if (refreshToken == null) { 
            response.setStatus(401); 
            return; 
        }

        try {
            Jws<Claims> claims = jwtService.parseToken(refreshToken);
            String userId = claims.getBody().getSubject();
            
            var maybeRt = refreshTokenRepository.findByToken(refreshToken);
            if (maybeRt.isEmpty() || !maybeRt.get().getUserId().equals(userId)) {
                response.setStatus(401); 
                return;
            }
            
            AppUser user = appUserRepository.findById(userId).orElseThrow();

            String newAccess = jwtService.generateAccessToken(user);
            Cookie accessCookie = new Cookie("ACCESS_TOKEN", newAccess);
            accessCookie.setHttpOnly(true); 
            accessCookie.setSecure(false); // Set to true in production
            accessCookie.setPath("/");
            accessCookie.setMaxAge(60*15);
            response.addCookie(accessCookie);
            response.setStatus(200);
        } catch (Exception ex) {
            response.setStatus(401);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("REFRESH_TOKEN".equals(c.getName())) {
                    refreshToken = c.getValue();
                    break;
                }
            }
        }
        
        if (refreshToken != null) {
            refreshTokenRepository.findByToken(refreshToken)
                    .ifPresent(rt -> refreshTokenRepository.delete(rt));
        }
        
        Cookie delA = new Cookie("ACCESS_TOKEN", ""); 
        delA.setMaxAge(0); 
        delA.setPath("/");
        Cookie delR = new Cookie("REFRESH_TOKEN", ""); 
        delR.setMaxAge(0); 
        delR.setPath("/");
        response.addCookie(delA); 
        response.addCookie(delR);
        response.setStatus(200);
    }

    @GetMapping("/profile")
    public Object profile(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return Map.of("loggedIn", false);
        }
        return Map.of("loggedIn", true, "user", authentication.getPrincipal());
    }
}