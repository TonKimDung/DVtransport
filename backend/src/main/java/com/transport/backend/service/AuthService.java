package com.transport.backend.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.transport.backend.dto.auth.LoginRequest;
import com.transport.backend.dto.auth.LoginResponse;
import com.transport.backend.entity.User;
import com.transport.backend.entity.UserLog;
import com.transport.backend.repository.UserLogRepository;
import com.transport.backend.repository.UserRepository;
import com.transport.backend.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserLogRepository userLogRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Sai tên đăng nhập hoặc mật khẩu"));

    if (Boolean.FALSE.equals(user.getIsActive())) {
        throw new RuntimeException("Tài khoản đã bị khóa");
    }

    boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());

    if (!isMatch) {
        throw new RuntimeException("Sai tên đăng nhập hoặc mật khẩu");
    }

    // 👉 tạo token
    String token = jwtUtil.generateToken(user.getUsername());

    userLogRepository.save(UserLog.builder()
            .user(user)
            .action("Đăng nhập hệ thống")
            .createdAt(LocalDateTime.now())
            .build());

    return LoginResponse.builder()
            .token(token) // 👈 thêm dòng này
            .userId(user.getId())
            .username(user.getUsername())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .roleName(user.getRole() != null ? user.getRole().getRoleName() : null)
            .isActive(user.getIsActive())
            .message("Đăng nhập thành công")
            .build();
}
}