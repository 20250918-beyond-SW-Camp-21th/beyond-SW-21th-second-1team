package com.valetparker.chagok.using.repository;

import com.valetparker.chagok.using.dto.UsingDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsingRepository extends JpaRepository<UsingDto, Long> {
}
