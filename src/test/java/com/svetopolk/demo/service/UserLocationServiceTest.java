package com.svetopolk.demo.service;

import com.svetopolk.demo.dto.Location;
import com.svetopolk.demo.dto.LocationWithDate;
import com.svetopolk.demo.dto.UserDataRequest;
import com.svetopolk.demo.dto.UserLocationRequest;
import com.svetopolk.demo.dto.UserLocationResponse;
import com.svetopolk.demo.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserLocationServiceTest {

    @Autowired
    LocationService locationService;

    @Autowired
    UserService userService;

    private UUID userId;

    @BeforeEach
    void init() {
        userId = UUID.randomUUID();
        var userDataRequest = new UserDataRequest(userId, "alex.schmid@gmail.com", "Alex", "Schmid");
        userService.save(userDataRequest);
    }

    @Test
    void getLastLocation() {
        var time = LocalDateTime.of(2000, 1, 15, 15, 0, 0);
        var location = new Location(52.25742342295784, 10.540583401747602);

        locationService.saveLocation(new UserLocationRequest(userId, time, location));
        locationService.saveLocation(new UserLocationRequest(userId, time.minusSeconds(1), location));
        locationService.saveLocation(new UserLocationRequest(userId, time.minusSeconds(2), location));

        var expectedUserLocation = new UserLocationResponse(userId, "Alex", "Schmid", location);
        var actualUserLocation = locationService.getLocation(userId);
        assertThat(actualUserLocation, is(expectedUserLocation));
    }

    @Test
    void getLocationRange() {
        var time = LocalDateTime.of(2000, 1, 15, 15, 0, 0);
        var location = new Location(52.25742342295784, 10.540583401747602);

        locationService.saveLocation(new UserLocationRequest(userId, time, location));
        locationService.saveLocation(new UserLocationRequest(userId, time.minusDays(1), location));
        locationService.saveLocation(new UserLocationRequest(userId, time.plusHours(1), location));
        locationService.saveLocation(new UserLocationRequest(userId, time.plusHours(25), location));

        var actualLocationRangeRespond = locationService.getLocations(userId, time.minusSeconds(1), time.plusDays(1));
        assertThat(actualLocationRangeRespond.userId(), is(userId));
        assertThat(actualLocationRangeRespond.locationWithDate(), hasSize(2));
        assertThat(actualLocationRangeRespond.locationWithDate().get(0), is(new LocationWithDate(time, location)));
    }

    @Test
    void getLocationForNonExistedUser() {
        assertThrows(UserNotFoundException.class, () -> locationService.getLocation(UUID.randomUUID()));
    }

}