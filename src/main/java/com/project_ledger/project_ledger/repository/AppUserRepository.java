package com.project_ledger.project_ledger.repository;

import com.project_ledger.project_ledger.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByProviderAndProviderId(String provider, String providerId);
    Optional<AppUser> findByEmail(String email);
}