package com.example.ai_expense_backend.service;

import com.example.ai_expense_backend.dto.CreateUserRequest;
import com.example.ai_expense_backend.dto.UserResponse;
import com.example.ai_expense_backend.entity.AppUser;
import com.example.ai_expense_backend.exception.DuplicateResourceException;
import com.example.ai_expense_backend.exception.ResourceNotFoundException;
import com.example.ai_expense_backend.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (appUserRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        AppUser user = AppUser.builder()
                .fullName(request.fullName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .build();

        AppUser savedUser = appUserRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(AppUser user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt()
        );
    }
}