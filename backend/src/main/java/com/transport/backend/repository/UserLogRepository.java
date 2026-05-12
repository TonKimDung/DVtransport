package com.transport.backend.repository;

import com.transport.backend.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLogRepository extends JpaRepository<UserLog, Integer> {
    List<UserLog> findByUserId(Integer userId);
}