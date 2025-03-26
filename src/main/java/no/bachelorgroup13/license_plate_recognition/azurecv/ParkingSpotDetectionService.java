package no.bachelorgroup13.license_plate_recognition.azurecv;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.bachelorgroup13.license_plate_recognition.azurecv.model.ParkingSpot;

@Service
public class ParkingSpotDetectionService {
    private final String endpoint;
    private final String subscriptionKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ParkingSpotDetectionService(String endpoint, String subscriptionKey) {
        this.endpoint = endpoint;
        this.subscriptionKey = subscriptionKey;
    }

    public List<ParkingSpot> detectParkingSpots(File imageFile) throws IOException, InterruptedException {
        String operationLocation = sendImageForAnalysis(imageFile);
        JsonNode analysisResult = getAnalysisResult(operationLocation);

        return extractParkingSpots(analysisResult);
    }

    private String sendImageForAnalysis(File imageFile) {
        // Send image to Azure Computer Vision API and return the operation location
        return null;
    }

    private JsonNode getAnalysisResult(String operationLocation) throws IOException, InterruptedException {
        // Poll the operation location until the analysis is done and return the result
        return null;
    }

    private List<ParkingSpot> extractParkingSpots(JsonNode analysisResult) {
        // Extract parking spots from the analysis result
        return null;
    }

}
