package no.bachelorgroup13.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "spot_number")
    private String spotNumber;

    @Column(name = "user_id", insertable = true, updatable = true)
    private UUID userId;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "license_plate")
    private String licensePlate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "estimated_departure")
    private ZonedDateTime estimatedDeparture;

    @PrePersist
    @PreUpdate
    private void ensureNorwayTimezone() {
        if (estimatedDeparture != null) {
            estimatedDeparture = estimatedDeparture.withZoneSameInstant(ZoneId.of("Europe/Oslo"));
        }
    }
}
