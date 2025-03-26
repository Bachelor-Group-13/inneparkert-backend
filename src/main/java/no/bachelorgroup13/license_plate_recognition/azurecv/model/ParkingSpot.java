package no.bachelorgroup13.license_plate_recognition.azurecv.model;

import java.util.List;

public class ParkingSpot {
    private int id;
    private String spotNumber;
    private List<Integer> boundingBox;

    public ParkingSpot() {
    }

    public ParkingSpot(int id, String spotNumber, List<Integer> boundingBox) {
        this.id = id;
        this.spotNumber = spotNumber;
        this.boundingBox = boundingBox;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }

    public List<Integer> getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(List<Integer> boundingBox) {
        this.boundingBox = boundingBox;
    }
}
