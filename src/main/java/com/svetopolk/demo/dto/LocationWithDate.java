package com.svetopolk.demo.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LocationWithDate(
        @NotNull
        LocalDateTime createdOn,
        @NotNull
        Location location
) {
}
