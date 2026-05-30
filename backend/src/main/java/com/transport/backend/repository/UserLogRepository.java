package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, Integer> {
    List<UserLog> findByUserId(Integer userId);
    List<UserLog> findAllByOrderByCreatedAtDesc();

    List<UserLog> findByUserIdOrderByCreatedAtDesc(Integer userId);
}