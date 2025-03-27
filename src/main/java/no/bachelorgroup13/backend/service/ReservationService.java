package no.bachelorgroup13.backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.entity.Reservation;
import no.bachelorgroup13.backend.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
  private final ReservationRepository reservationRepository;

  public List<Reservation> getAllReservations() {
    return reservationRepository.findAll();
  }

  public Optional<Reservation> getReservationById(Integer id) {
    return reservationRepository.findById(id);
  }

  public List<Reservation> getReservationsByUserId(UUID userId) {
    return reservationRepository.findByUserId(userId);
  }

  public List<Reservation> getReservationsByDate(LocalDate date) {
    return reservationRepository.findByReservationDate(date);
  }

  public List<Reservation> getReservationsByLicensePlate(String licensePlate) {
    return reservationRepository.findByLicensePlate(licensePlate);
  }

  public Reservation createReservation(Reservation reservation) {
    return reservationRepository.save(reservation);
  }

  public Reservation updateReservation(Reservation reservation) {
    return reservationRepository.save(reservation);
  }

  public void deleteReservation(Integer id) {
    reservationRepository.deleteById(id);
  }
}
