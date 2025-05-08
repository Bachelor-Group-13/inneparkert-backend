package no.bachelorgroup13.backend.features.push.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushSubscriptionDto {
    @NotNull(message = "Endpoint is required")
    private String endpoint;

    @NotNull(message = "P256dh key is required")
    private String p256dh;

    @NotNull(message = "Auth secret is required")
    private String auth;

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
