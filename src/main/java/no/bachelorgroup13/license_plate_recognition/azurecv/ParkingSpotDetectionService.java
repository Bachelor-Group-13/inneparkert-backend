package no.bachelorgroup13.license_plate_recognition.azurecv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
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

    private String sendImageForAnalysis(File imageFile) throws IOException {
        // Send image to Azure Computer Vision API and return the operation location
        URI analyzeUri = URI.create(endpoint + "/vision/v3.2/analyze?visualFeatures=Objects,Lines");
        HttpURLConnection connection = (HttpURLConnection) analyzeUri.toURL().openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setDoOutput(true);

        try (FileInputStream imageInputStream = new FileInputStream(imageFile);
                OutputStream outputStream = connection.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = imageInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);

            }
        }
        int responseCode = connection.getResponseCode();
        if (responseCode != 202) {
            String errorMessage = readStream(connection.getErrorStream());
            throw new IOException(
                    "Failed to send image to Azure. HTTP " + responseCode + ": " + errorMessage);
        }
        String operationLocation = connection.getHeaderField("Operation-Location");
        if (operationLocation == null || operationLocation.isEmpty()) {
            String errorMessage = readStream(connection.getInputStream());
            return errorMessage;
        }
        return operationLocation;
    }

    private JsonNode getAnalysisResult(String operationLocationOrResult) throws IOException, InterruptedException {
        // Poll the operation location until the analysis is done and return the result
        if (operationLocationOrResult.startsWith("{")) {
            return objectMapper.readTree(operationLocationOrResult);
        }

        while (true) {
            URI operationUri = URI.create(operationLocationOrResult);
            HttpURLConnection connection = (HttpURLConnection) operationUri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                String errorMessage = readStream(connection.getErrorStream());
                throw new IOException(
                        "Failed to get analysis result from Azure. HTTP " + responseCode + ": " + errorMessage);
            }

            String json = readStream(connection.getInputStream());
            JsonNode response = objectMapper.readTree(json);

            String status = response.get("status").asText();
            if (!"notStarted".equalsIgnoreCase(status) && !"running".equalsIgnoreCase(status)) {
                return response;
            }
            Thread.sleep(1000);
        }
    }

    private List<ParkingSpot> extractParkingSpots(JsonNode analysisResult) {
        // Extract parking spots from the analysis result
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        JsonNode linesNode = analysisResult.path("lines");
        if (linesNode.isArray()) {
            int imageWidth = analysisResult.get("metadata").path("width").asInt();
            int imageHeight = analysisResult.get("metadata").path("height").asInt();

            int spotWidth = imageWidth / 7;
            int spotHeight = imageHeight / 2;

            int spotId = 1;
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 7; col++) {
                    String spotNumber = String.format("%d%s", col + 1, row == 0 ? "A" : "B");
                    List<Integer> boundingBox = Arrays.asList(
                            col * spotWidth,
                            row * spotHeight,
                            spotWidth,
                            spotHeight);
                    parkingSpots.add(new ParkingSpot(spotId++, spotNumber, boundingBox));
                }
            }
        }
        return parkingSpots;
    }

    private String readStream(InputStream inputStream) throws IOException {
        // Read the stream and return the content as a string
        if (inputStream == null) {
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);

            }
            return stringBuilder.toString();
        }
    }
}
