package com.transport.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
     @EntityGraph(attributePaths = "role")
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}