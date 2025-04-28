package no.bachelorgroup13.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    private UUID id;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "second_license_plate")
    private String secondLicensePlate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "role", nullable = false)
    private String role;
}
