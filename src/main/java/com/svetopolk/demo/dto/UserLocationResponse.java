package com.svetopolk.demo.dto;

import java.util.UUID;

public record UserLocationResponse(
        UUID userId,
        String firstName,
        String secondName,
        Location location
) {
}
