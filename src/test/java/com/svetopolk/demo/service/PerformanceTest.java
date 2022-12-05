package com.svetopolk.demo.service;

import com.svetopolk.demo.dto.Location;
import com.svetopolk.demo.dto.UserDataRequest;
import com.svetopolk.demo.dto.UserLocationRequest;
import com.svetopolk.demo.dto.UserLocationResponse;
import com.svetopolk.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PerformanceTest {

    @Autowired
    LocationService locationService;

    @Autowired
    UserService userService;

    @Test
    void performanceTest() {
        int numberOfUsers = 100;
        int numberOfDays = 30;

        var testTime = timeCut(0, "start test");

        List<UUID> users = new ArrayList<>();
        IntStream.range(0, numberOfUsers).forEach(x -> {
                    UUID userId = UUID.randomUUID();
                    users.add(userId);
                    var userDataRequest = new UserDataRequest(userId, "user" + x + "@g.com", "User_" + x, "User");
                    userService.save(userDataRequest);
                }
        );

        testTime = timeCut(testTime, numberOfUsers + " users are generated sequentially");

        var startLocationTime = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        var location = new Location(52.25742342295784, 10.540583401747602);

        var numberLocations = 60 * 24 * numberOfDays;
        users.stream().parallel().forEach(user -> IntStream.range(0, numberLocations)
                .forEach(x -> {
                            var userLocation = new UserLocationRequest(user, startLocationTime.plusMinutes(x), location);
                            locationService.saveLocation(userLocation);
                        }
                ));

        testTime = timeCut(testTime, numberLocations + " locations are generated for each user in parallel");

        users.forEach(user -> {
            var actualUserLocation = locationService.getLocation(user);
            assertThat(actualUserLocation.userId(), is(user));
        });
        testTime = timeCut(testTime, "getLocation() for each " + numberOfUsers + " users sequentially");

        users.forEach(user -> {
            Random random = new Random();
            var from = startLocationTime.plusDays(random.nextInt(0, numberOfDays));
            var to = from.plusDays(7);
            var actualUserLocation = locationService.getLocations(user, from, to);
            assertThat(actualUserLocation.userId(), is(user));
        });

        timeCut(testTime, "getLocations() for random day for 1 week for each user sequentially");
    }

    long timeCut(long time, String message) {
        if (time == 0) {
            System.out.println(message);
        } else {
            var duration = (System.currentTimeMillis() - time);
            if (duration > 2000) {
                System.out.println(message + ": " + duration / 1000 + " seconds");
            } else {
                System.out.println(message + ": " + duration + " milliseconds");
            }
        }
        return System.currentTimeMillis();
    }
}