package com.svetopolk.demo.rest;

import com.svetopolk.demo.dto.ApiResponse;
import com.svetopolk.demo.dto.UserInfoRequest;
import com.svetopolk.demo.dto.UserLocationRangeResponse;
import com.svetopolk.demo.dto.UserLocationResponse;
import com.svetopolk.demo.service.LocationService;
import com.svetopolk.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/users")
public class UserController {

    private final LocationService locationService;
    private final UserService userService;

    @PutMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse saveUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        userService.save(userInfoRequest);
        return ApiResponse.ok();
    }

    @GetMapping(value = "/{userId}/locations", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserLocationRangeResponse getLocationRange(
            @PathVariable String userId,
            @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return locationService.getLocations(UUID.fromString(userId), from, to);
    }

    @GetMapping(value = "/{userId}/location", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserLocationResponse getLatestLocation(@PathVariable String userId) {
        return locationService.getLocation(UUID.fromString(userId));
    }
}
