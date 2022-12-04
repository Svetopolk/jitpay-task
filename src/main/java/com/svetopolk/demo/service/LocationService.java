package com.svetopolk.demo.service;

import com.svetopolk.demo.dto.Location;
import com.svetopolk.demo.dto.LocationWithDate;
import com.svetopolk.demo.dto.UserLocationResponse;
import com.svetopolk.demo.dto.UserLocationRequest;
import com.svetopolk.demo.dto.UserLocationRangeResponse;
import com.svetopolk.demo.entity.UserLocation;
import com.svetopolk.demo.exception.LocationNotFoundException;
import com.svetopolk.demo.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserService userService;

    public void store(UserLocationRequest userLocationRequest) {
        System.out.println("store" + userLocationRequest);
        UserLocation userLocation = new UserLocation(
                UUID.fromString(userLocationRequest.userId()),
                userLocationRequest.createdOn(),
                userLocationRequest.location().latitude(),
                userLocationRequest.location().longitude()
        );
        locationRepository.save(userLocation);
    }

    public UserLocationRangeResponse getLocations(UUID userId, LocalDateTime from, LocalDateTime to) {
        System.out.println("userId=" + userId + " from=" + from + " to=" + to);
        var user = userService.get(userId);
        List<LocationWithDate> locations = locationRepository.findRange(userId, from, to)
                .stream()
                .map(x -> new LocationWithDate(x.getCreatedOn(), new Location(x.getLatitude(), x.getLongitude())))
                .toList();
        return new UserLocationRangeResponse(user.getUserId().toString(), locations);
    }

    public UserLocationResponse getLocation(UUID userId) {
        System.out.println("userId=" + userId);
        var user = userService.get(userId);
        UserLocation userLocation = locationRepository.findLatest(userId);
        if (userLocation == null) {
            throw new LocationNotFoundException();
        }
        return new UserLocationResponse(
                userLocation.getUserId().toString(),
                user.getFirstName(),
                user.getSecondName(),
                new Location(userLocation.getLatitude(), userLocation.getLongitude())
        );
    }
}
