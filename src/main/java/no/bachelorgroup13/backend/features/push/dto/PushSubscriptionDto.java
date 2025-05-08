package no.bachelorgroup13.backend.features.push.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushSubscriptionDto {
    @Schema(description = "The endpoint URL for the push subscription.")
    @NotNull(message = "Endpoint is required")
    private String endpoint;

    @Schema(description = "The public key for the push subscription.")
    @NotNull(message = "P256dh key is required")
    private String p256dh;

    @Schema(description = "The authentication secret for the push subscription.")
    @NotNull(message = "Auth secret is required")
    private String auth;

    @Schema(description = "The ID of the user associated with the push subscription.")
    private UUID userId;

    @Override
    public String toString() {
        return "PushSubscriptionDto{"
                + "endpoint='"
                + endpoint
                + '\''
                + ", p256dh='"
                + (p256dh != null ? "[SET]" : "[NOT SET]")
                + '\''
                + ", auth='"
                + (auth != null ? "[SET]" : "[NOT SET]")
                + '\''
                + '}';
    }
}
