package com.ohgiraffers.userservice.command.repository;

import com.ohgiraffers.userservice.command.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, Long> {

}
