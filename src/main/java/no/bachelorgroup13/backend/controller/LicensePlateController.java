package no.bachelorgroup13.backend.controller;

import java.io.File;
import java.util.List;
import no.bachelorgroup13.backend.azurecv.ComputerVisionService;
import no.bachelorgroup13.backend.dto.PlateDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/license-plate")
@CrossOrigin(origins = "*")
public class LicensePlateController {
    private final ComputerVisionService computerVisionService;

    public LicensePlateController(ComputerVisionService computerVisionService) {
        this.computerVisionService = computerVisionService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> recognizePlate(@RequestParam("image") MultipartFile image) {
        try {
            // Save to a temp file
            File tempFile = File.createTempFile("image", ".jpg");
            image.transferTo(tempFile);

            // Get plates from Azure
            List<PlateDto> plates = computerVisionService.getLicensePlates(tempFile);

            // Return JSON with "license_plates"
            return ResponseEntity.ok(new LicensePlatesResponse(plates));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Error recognizing license plate:" + e.getMessage());
        }
    }

    // JSON response object
    static class LicensePlatesResponse {
        private List<PlateDto> license_plates;

        public LicensePlatesResponse(List<PlateDto> plates) {
            this.license_plates = plates;
        }

        public List<PlateDto> getLicense_plates() {
            return license_plates;
        }

        public void setLicense_plates(List<PlateDto> license_plates) {
            this.license_plates = license_plates;
        }
    }
}
