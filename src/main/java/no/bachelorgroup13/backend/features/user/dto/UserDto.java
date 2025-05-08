package no.bachelorgroup13.backend.features.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.bachelorgroup13.backend.features.auth.security.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User Information")
public class UserDto {
    @Schema(description = "The ID of the user.")
    private UUID id;

    @Schema(description = "The name of the user.")
    private String name;

    @Schema(description = "The email of the user.")
    private String email;

    @Schema(description = "The phone number of the user.")
    private String phoneNumber;

    @Schema(description = "The license plate of the user.")
    private String licensePlate;

    @Schema(description = "The second license plate of the user (Optional).")
    private String secondLicensePlate;

    @Schema(description = "The role of the user.")
    private Role role;
}
