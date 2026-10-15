package com.portfolio.rest_service.service;

import com.portfolio.rest_service.dto.CreateUserRequest;
import com.portfolio.rest_service.dto.UserResponse;
import com.portfolio.rest_service.entity.UserEntity;
import com.portfolio.rest_service.exception.ResourceNotFoundException;
import com.portfolio.rest_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional 
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("User with email " + request.email() + " already exists.");
        }

        UserEntity user = UserEntity.builder()
            .email(request.email())
            .fullName(request.fullName())
            .status("ACTIVE")
            .build();

        UserEntity savedUser = userRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return UserResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(UserResponse::fromEntity)
            .toList();
    }
}
