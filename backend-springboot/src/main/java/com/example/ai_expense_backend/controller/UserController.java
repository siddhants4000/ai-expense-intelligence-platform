package com.example.ai_expense_backend.controller;

import com.example.ai_expense_backend.dto.ApiResponse;
import com.example.ai_expense_backend.dto.CreateUserRequest;
import com.example.ai_expense_backend.dto.UserResponse;
import com.example.ai_expense_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        UserResponse response = userService.createUser(request);
        return ApiResponse.success("User created successfully", response);
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ApiResponse.success("Users fetched successfully", response);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(
            @PathVariable UUID userId
    ) {
        UserResponse response = userService.getUserById(userId);
        return ApiResponse.success("User fetched successfully", response);
    }
}