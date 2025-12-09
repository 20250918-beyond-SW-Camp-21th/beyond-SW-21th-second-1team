package com.ohgiraffers.userservice.auth.repository;

import com.ohgiraffers.userservice.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteByEmail(String email);
}
