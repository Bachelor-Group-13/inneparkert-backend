package no.bachelorgroup13.backend.features.licenseplate.azurecv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalyzeResult {
    private List<ReadResult> readResults;

    public List<ReadResult> getReadResults() {
        return readResults;
    }

    public void setReadResults(List<ReadResult> readResults) {
        this.readResults = readResults;
    }
}
