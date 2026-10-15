package com.portfolio.rest_service.dto;

import com.portfolio.rest_service.entity.UserEntity;
import java.time.LocalDateTime;

public record UserResponse(
    Long id,
    String email,
    String fullName,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static UserResponse fromEntity(UserEntity userEntity) {
        return new UserResponse(
            userEntity.getId(),
            userEntity.getEmail(),
            userEntity.getFullName(),
            userEntity.getStatus(),
            userEntity.getCreatedAt(),
            userEntity.getUpdatedAt()
        );
    }
}