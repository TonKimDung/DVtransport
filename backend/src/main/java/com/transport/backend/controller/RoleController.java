package com.transport.backend.controller;

import com.transport.backend.dto.auth.RoleRequest;
import com.transport.backend.dto.auth.RoleResponse;
import com.transport.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public RoleResponse getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    @PostMapping
    public RoleResponse createRole(@RequestBody RoleRequest request) {
        return roleService.createRole(request);
    }

    @PutMapping("/{id}")
    public RoleResponse updateRole(
            @PathVariable Integer id,
            @RequestBody RoleRequest request
    ) {
        return roleService.updateRole(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return "Xóa vai trò thành công";
    }
}