package com.authenticationapplication.repository;

import com.authenticationapplication.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);
}
