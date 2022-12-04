package com.svetopolk.demo.dto;

import java.util.List;

public record UserLocationRangeResponse(
        String userId,
        List<LocationWithDate> locationWithDate
) {
}
