package com.svetopolk.demo.dto;

import java.util.List;
import java.util.UUID;

public record UserLocationRangeResponse(
        UUID userId,
        List<LocationWithDate> locationWithDate
) {
}
