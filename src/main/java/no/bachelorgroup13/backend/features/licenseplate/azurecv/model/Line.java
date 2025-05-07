package no.bachelorgroup13.backend.features.licenseplate.azurecv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Line {
    private String text;

    @JsonProperty("boundingBox")
    private int[] boundingBox;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int[] getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(int[] boundingBox) {
        this.boundingBox = boundingBox;
    }
}
