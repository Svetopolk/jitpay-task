package com.svetopolk.demo.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserLocationRequest(
        @NotNull(message = "userId is mandatory")
        UUID userId,
        @NotNull(message = "createdOn is mandatory")
        LocalDateTime createdOn,
        @NotNull(message = "location is mandatory")
        Location location
) {
}
