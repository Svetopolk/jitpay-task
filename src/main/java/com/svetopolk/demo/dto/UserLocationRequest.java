package com.svetopolk.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record UserLocationRequest(
        @NotBlank(message = "userId is mandatory")
        String userId,
        @NotNull(message = "createdOn is mandatory")
        LocalDateTime createdOn,
        @NotNull(message = "location is mandatory")
        Location location
) {
}
