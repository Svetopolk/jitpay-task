package com.svetopolk.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@IdClass(UserLocation.UserLocationId.class)
@Table(name = "LOCATIONS")
@Data
@AllArgsConstructor()
@NoArgsConstructor
public class UserLocation {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Id
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Data
    public static class UserLocationId implements Serializable {
        private UUID userId;
        private LocalDateTime createdOn;
    }
}
