package com.svetopolk.demo.dto;

import java.time.LocalDateTime;

public record LocationWithDate(
        LocalDateTime createdOn,
        Location location
) {
}
