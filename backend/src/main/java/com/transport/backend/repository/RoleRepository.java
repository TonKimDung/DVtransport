package com.transport.backend.repository;

import com.transport.backend.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
}