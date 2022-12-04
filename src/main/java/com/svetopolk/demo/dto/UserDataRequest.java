package com.svetopolk.demo.dto;

import jakarta.validation.constraints.NotNull;

public record UserDataRequest(
        @NotNull(message = "userId is mandatory")
        String userId,
        @NotNull(message = "email is mandatory")
        String email,
        @NotNull(message = "firstName is mandatory")
        String firstName,
        @NotNull(message = "secondName is mandatory")
        String secondName
) {
}
