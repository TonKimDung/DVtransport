package com.transport.backend.service;

import com.transport.backend.dto.auth.UserRequest;
import com.transport.backend.dto.auth.UserResponse;
import com.transport.backend.entity.Role;
import com.transport.backend.entity.User;
import com.transport.backend.entity.UserLog;
import com.transport.backend.repository.RoleRepository;
import com.transport.backend.repository.UserLogRepository;
import com.transport.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserLogRepository userLogRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roleId(user.getRole() != null ? user.getRole().getId() : null)
                .roleName(user.getRole() != null ? user.getRole().getRoleName() : null)
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        return toResponse(user);
    }

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));

        User user = User.builder()
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(role)
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);

        userLogRepository.save(UserLog.builder()
                .user(saved)
                .action("Tạo tài khoản người dùng: " + saved.getUsername())
                .createdAt(LocalDateTime.now())
                .build());

        return toResponse(saved);
    }

    public UserResponse updateUser(Integer id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(role);

        if (request.getIsActive() != null) {
            user.setIsActive(request.getIsActive());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        User saved = userRepository.save(user);

        userLogRepository.save(UserLog.builder()
                .user(saved)
                .action("Cập nhật tài khoản người dùng: " + saved.getUsername())
                .createdAt(LocalDateTime.now())
                .build());

        return toResponse(saved);
    }

    public UserResponse lockUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        user.setIsActive(false);
        User saved = userRepository.save(user);

        userLogRepository.save(UserLog.builder()
                .user(saved)
                .action("Khóa tài khoản người dùng: " + saved.getUsername())
                .createdAt(LocalDateTime.now())
                .build());

        return toResponse(saved);
    }

    public UserResponse unlockUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        user.setIsActive(true);
        User saved = userRepository.save(user);

        userLogRepository.save(UserLog.builder()
                .user(saved)
                .action("Mở khóa tài khoản người dùng: " + saved.getUsername())
                .createdAt(LocalDateTime.now())
                .build());

        return toResponse(saved);
    }
}