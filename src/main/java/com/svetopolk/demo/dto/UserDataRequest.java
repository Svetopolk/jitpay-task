package com.svetopolk.demo.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserDataRequest(
        @NotNull(message = "userId is mandatory")
        UUID userId,
        @NotNull(message = "email is mandatory")
        String email, //TODO email validation
        @NotNull(message = "firstName is mandatory")
        String firstName,
        @NotNull(message = "secondName is mandatory")
        String secondName
) {
}
