package no.bachelorgroup13.backend.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.entity.Reservation;
import no.bachelorgroup13.backend.repository.PushSubscriptionRepository;
import no.bachelorgroup13.backend.service.PushServiceWrapper;
import no.bachelorgroup13.backend.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final PushSubscriptionRepository pushRepository;
    private final PushServiceWrapper pushService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer id) {
        return reservationService
                .getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Reservation>> getReservationsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reservationService.getReservationsByDate(date));
    }

    @GetMapping("/license-plate/{licensePlate}")
    public ResponseEntity<List<Reservation>> getReservationsByLicensePlate(
            @PathVariable String licensePlate) {
        return ResponseEntity.ok(reservationService.getReservationsByLicensePlate(licensePlate));
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        UUID userId = reservation.getUserId();

        if (reservationService.hasActiveReservation(userId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Reservation saved = reservationService.createReservation(reservation);

        pushRepository.findAllByUserId(userId).forEach(sub -> {
            try {
                pushService.sendPush(
                        sub,
                        "Spot " + saved.getSpotNumber() + " reserved!",
                        "Check if you parked in someone");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Integer id, @RequestBody Reservation reservation) {
        return reservationService
                .getReservationById(id)
                .map(
                        existingReservation -> {
                            reservation.setId(id);
                            return ResponseEntity.ok(
                                    reservationService.updateReservation(reservation));
                        })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        return reservationService
                .getReservationById(id)
                .map(
                        reservation -> {
                            reservationService.deleteReservation(id);
                            return ResponseEntity.noContent().<Void>build();
                        })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/all")
    @PreAuthorize("hasRole('ROLE_DEVELOPER')")
    public ResponseEntity<Void> deleteAllReservations() {
        reservationService.deleteAllReservations();
        return ResponseEntity.noContent().build();
    }
}
