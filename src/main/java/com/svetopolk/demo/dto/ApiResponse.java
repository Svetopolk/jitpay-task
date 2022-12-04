package com.svetopolk.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiResponse(
        String status,
        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        String message
) {
    public static ApiResponse ok() {
        return new ApiResponse("ok", null);
    }

    public static ApiResponse failed(String message) {
        return new ApiResponse("failed", message);
    }
}
