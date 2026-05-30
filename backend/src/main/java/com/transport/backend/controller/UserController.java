package com.transport.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.auth.UserLogResponse;
import com.transport.backend.dto.auth.UserRequest;
import com.transport.backend.dto.auth.UserResponse;
import com.transport.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Integer id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @PatchMapping("/{id}/lock")
    public UserResponse lockUser(@PathVariable Integer id) {
        return userService.lockUser(id);
    }

    @PatchMapping("/{id}/unlock")
    public UserResponse unlockUser(@PathVariable Integer id) {
        return userService.unlockUser(id);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<UserLogResponse>> getAllUserLogs() {
        return ResponseEntity.ok(userService.getAllUserLogs());
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<UserLogResponse>> getUserLogsByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserLogsByUserId(id));
    }

}