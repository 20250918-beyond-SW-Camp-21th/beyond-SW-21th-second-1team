package com.valetparker.chagok.using.repository;

import com.valetparker.chagok.using.domain.Using;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsingRepository extends JpaRepository<Using, Long> {
}
