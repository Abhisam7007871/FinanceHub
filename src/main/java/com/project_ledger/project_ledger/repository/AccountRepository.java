package com.project_ledger.project_ledger.repository;

import com.project_ledger.project_ledger.entity.Account;
import com.project_ledger.project_ledger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
}