package com.valetparker.chagok.user.command.repository;

import com.valetparker.chagok.user.command.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByCarNumber(String carNumber);
    Optional<User> findByUserNo(Long userNo);
    Optional<User> findByRole(String role);

    User save(User user);
}
