package no.bachelorgroup13.license_plate_recognition.controller;

import java.io.File;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import no.bachelorgroup13.license_plate_recognition.azurecv.ParkingSpotDetectionService;
import no.bachelorgroup13.license_plate_recognition.azurecv.model.ParkingSpot;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/parking-spots")
@CrossOrigin(origins = "*")
public class ParkingSpotController {
    private final ParkingSpotDetectionService parkingSpotDetectionService;

    public ParkingSpotController(ParkingSpotDetectionService parkingSpotDetectionService) {
        this.parkingSpotDetectionService = parkingSpotDetectionService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> detectParkingSpots(@RequestParam("image") MultipartFile image) {
        try {
            File tempFile = File.createTempFile("image", ".jpg");
            image.transferTo(tempFile);

            List<ParkingSpot> parkingSpots = parkingSpotDetectionService.detectParkingSpots(tempFile);

            return ResponseEntity.ok(new ParkingSpotResponse(parkingSpots));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error detecting parking spots: " + e.getMessage());
        }
    }

    static class ParkingSpotResponse {
        private List<ParkingSpot> parking_spots;

        public ParkingSpotResponse(List<ParkingSpot> parking_spots) {
            this.parking_spots = parking_spots;
        }

        public List<ParkingSpot> getParkingSpots() {
            return parking_spots;
        }

        public void setParkingSpots(List<ParkingSpot> parking_spots) {
            this.parking_spots = parking_spots;
        }
    }

}
