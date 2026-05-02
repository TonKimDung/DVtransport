package com.transport.backend.controller;

import com.transport.backend.dto.auth.UserRequest;
import com.transport.backend.dto.auth.UserResponse;
import com.transport.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
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
}