package no.bachelorgroup13.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
    @Schema(description = "The message to be returned.")
    private String message;
}
