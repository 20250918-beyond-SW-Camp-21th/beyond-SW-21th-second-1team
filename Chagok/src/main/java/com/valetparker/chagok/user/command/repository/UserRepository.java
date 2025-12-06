package com.valetparker.chagok.user.command.repository;

import com.valetparker.chagok.user.command.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByCardNumber(String cardNumber);

}
