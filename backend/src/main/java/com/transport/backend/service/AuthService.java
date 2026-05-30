package com.transport.backend.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.transport.backend.dto.auth.LoginRequest;
import com.transport.backend.dto.auth.LoginResponse;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.User;
import com.transport.backend.entity.UserLog;
import com.transport.backend.repository.DriverRepository;
import com.transport.backend.repository.UserLogRepository;
import com.transport.backend.repository.UserRepository;
import com.transport.backend.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserLogRepository userLogRepository;
    private final JwtService jwtService;
    private final DriverRepository driverRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Sai tên đăng nhập hoặc mật khẩu"));

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }

        boolean isMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        );

        

        if (!isMatch) {
            throw new RuntimeException("Sai tên đăng nhập hoặc mật khẩu");
        }

        Driver driver = driverRepository.findByUser_Id(user.getId())
        .orElse(null);

        String token = jwtService.generateToken(user.getUsername());

        userLogRepository.save(UserLog.builder()
                .user(user)
                .action("Đăng nhập hệ thống")
                .createdAt(LocalDateTime.now())
                .build());

        return LoginResponse.builder()
        .token(token)
        .userId(user.getId())
        .driverId(driver != null ? driver.getId() : null)
        .username(user.getUsername())
        .fullName(user.getFullName())
        .email(user.getEmail())
        .roleName(user.getRole() != null ? user.getRole().getRoleName() : null)
        .isActive(user.getIsActive())
        .message("Đăng nhập thành công")
        .build();
    }
}