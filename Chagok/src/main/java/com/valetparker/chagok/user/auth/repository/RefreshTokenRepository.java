package com.valetparker.chagok.user.auth.repository;

import com.valetparker.chagok.user.auth.entity.RefreshToken;
import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteByEmail(String email);
}
