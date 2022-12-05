package com.svetopolk.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserInfoRequest(
        @NotNull(message = "userId is mandatory")
        UUID userId,
        @Email(message = "invalid email")
        String email,
        @NotNull(message = "firstName is mandatory")
        String firstName,
        @NotNull(message = "secondName is mandatory")
        String secondName
) {
}
