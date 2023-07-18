package com.tonykang22.ordinaryboard.repository;

import com.tonykang22.ordinaryboard.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
