package no.bachelorgroup13.backend.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class ReservationDto {
    private Integer id;
    private String spotNumber;
    private UUID userId;
    private LocalDate reservationDate;
    private String licensePlate;
    private ZonedDateTime estimatedDeparture;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private boolean anonymous;
    private boolean blockedSpot;
}
