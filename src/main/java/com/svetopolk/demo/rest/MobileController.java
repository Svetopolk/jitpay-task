package com.svetopolk.demo.rest;

import com.svetopolk.demo.dto.ApiResponse;
import com.svetopolk.demo.dto.UserLocationRequest;
import com.svetopolk.demo.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class MobileController {

    private final LocationService locationService;

    @PostMapping(value = "mobile/locations", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse saveLocation(@Valid @RequestBody UserLocationRequest userLocationRequest) {
        locationService.saveLocation(userLocationRequest);
        return ApiResponse.ok();
    }
}
