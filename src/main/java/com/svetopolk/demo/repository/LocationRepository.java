package com.svetopolk.demo.repository;

import com.svetopolk.demo.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<UserLocation, UserLocation.UserLocationId> {

    @Query(
            nativeQuery = true,
            value = "select * from locations where user_id = :userId and created_on between :from and :to order by created_on"
    )
    List<UserLocation> findRange(UUID userId, LocalDateTime from, LocalDateTime to);


    @Query(
            nativeQuery = true,
            value = "select * from locations where user_id = :userId ORDER BY created_on DESC LIMIT 1"
    )
    UserLocation findLatest(UUID userId);

}
