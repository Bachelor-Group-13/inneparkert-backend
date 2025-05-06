package no.bachelorgroup13.backend.dto;

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

    // Add getters and setters
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getP256dh() {
        return p256dh;
    }

    public void setP256dh(String p256dh) {
        this.p256dh = p256dh;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

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
