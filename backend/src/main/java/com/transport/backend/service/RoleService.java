package com.transport.backend.service;

import com.transport.backend.dto.auth.RoleRequest;
import com.transport.backend.dto.auth.RoleResponse;
import com.transport.backend.entity.Role;
import com.transport.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RoleResponse getRoleById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));
        return toResponse(role);
    }

    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.findByRoleName(request.getRoleName()).isPresent()) {
            throw new RuntimeException("Vai trò đã tồn tại");
        }

        Role role = Role.builder()
                .roleName(request.getRoleName())
                .build();

        return toResponse(roleRepository.save(role));
    }

    public RoleResponse updateRole(Integer id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));

        role.setRoleName(request.getRoleName());

        return toResponse(roleRepository.save(role));
    }

    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));

        roleRepository.delete(role);
    }
}