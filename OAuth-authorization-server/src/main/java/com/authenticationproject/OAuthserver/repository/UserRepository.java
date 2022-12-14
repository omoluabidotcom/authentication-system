package com.authenticationproject.OAuthserver.repository;

import com.authenticationapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {
    com.authenticationproject.OAuthserver.entity.User findByEmail(String email);
}
