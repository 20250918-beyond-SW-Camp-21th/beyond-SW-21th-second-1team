package com.ohgiraffers.userservice.command.repository;

import com.ohgiraffers.userservice.command.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByCarNumber(String carNumber);
    Optional<User> findByUserNo(Long userNo);
    Optional<User> findByRole(String role);

    User save(User user);
}
