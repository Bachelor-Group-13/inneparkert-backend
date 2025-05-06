package no.bachelorgroup13.backend.mapper;

import no.bachelorgroup13.backend.dto.ReservationDto;
import no.bachelorgroup13.backend.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationDto toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setSpotNumber(reservation.getSpotNumber());
        reservationDto.setUserId(reservation.getUserId());
        reservationDto.setReservationDate(reservation.getReservationDate());
        reservationDto.setLicensePlate(reservation.getLicensePlate());
        reservationDto.setEstimatedDeparture(
                reservation.getEstimatedDeparture() != null
                        ? reservation.getEstimatedDeparture()
                        : null);

        if (reservation.getUser() != null) {
            reservationDto.setUserName(reservation.getUser().getName());
            reservationDto.setUserEmail(reservation.getUser().getEmail());
            reservationDto.setUserPhoneNumber(reservation.getUser().getPhoneNumber());
        }
        return reservationDto;
    }

    public Reservation toEntity(ReservationDto reservationDto) {
        if (reservationDto == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setSpotNumber(reservationDto.getSpotNumber());
        reservation.setUserId(reservationDto.getUserId());
        reservation.setReservationDate(reservationDto.getReservationDate());
        reservation.setLicensePlate(reservationDto.getLicensePlate());
        if (reservationDto.getEstimatedDeparture() != null) {
            reservation.setEstimatedDeparture(reservationDto.getEstimatedDeparture());
        }
        return reservation;
    }
}
