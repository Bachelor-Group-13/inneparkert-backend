package no.bachelorgroup13.backend.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String licensePlate;
    private String secondLicensePlate;
}
