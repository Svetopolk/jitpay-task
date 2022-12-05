package com.svetopolk.demo.rest;

import com.svetopolk.demo.dto.ApiResponse;
import com.svetopolk.demo.dto.UserDataRequest;
import com.svetopolk.demo.dto.UserLocationResponse;
import com.svetopolk.demo.dto.UserLocationRangeResponse;
import com.svetopolk.demo.entity.User;
import com.svetopolk.demo.service.LocationService;
import com.svetopolk.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final LocationService locationService;
    private final UserService userService;

    @PutMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> user(@RequestBody UserDataRequest userDataRequest) {
        userService.save(userDataRequest);
        return new ResponseEntity<>(ApiResponse.ok(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}/locations", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserLocationRangeResponse> getLocationRange(
            @PathVariable String userId,
            @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        var locations = locationService.getLocations(UUID.fromString(userId), from, to);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}/location", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserLocationResponse> getLatestLocation(@PathVariable String userId) {
        var location = locationService.getLocation(UUID.fromString(userId));
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}
