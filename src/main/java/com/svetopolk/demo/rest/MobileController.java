package com.svetopolk.demo.rest;

import com.svetopolk.demo.dto.ApiResponse;
import com.svetopolk.demo.dto.UserLocationRequest;
import com.svetopolk.demo.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class MobileController {

    private final LocationService locationService;

    @PostMapping(value = "mobile/locations", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> storeLocation(@Valid @RequestBody  UserLocationRequest userLocationRequest) {
        locationService.store(userLocationRequest);
        return new ResponseEntity<>(ApiResponse.ok(), HttpStatus.OK);
    }
}
