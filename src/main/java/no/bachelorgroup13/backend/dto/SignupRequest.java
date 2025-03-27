package no.bachelorgroup13.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 6)
  private String password;

  @NotBlank
  private String name;

  private String licensePlate;

  private String phoneNumber;

}
