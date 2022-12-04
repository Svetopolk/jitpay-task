package com.svetopolk.demo.dto;

public record UserLocationResponse(
        String userId,
        String firstName,
        String secondName,
        Location location
) {
}
