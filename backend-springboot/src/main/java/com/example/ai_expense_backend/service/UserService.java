package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.CreateUserRequest;
import com.example.ai_expense_backend.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(UUID userId);
}