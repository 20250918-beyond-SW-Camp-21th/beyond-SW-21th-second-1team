package com.valetparker.chagok.using.repository;

import com.valetparker.chagok.using.dto.UsingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsingRepository extends JpaRepository<UsingDto, Long> {
}
