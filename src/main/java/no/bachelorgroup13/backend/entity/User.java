package no.bachelorgroup13.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "second_license_plate")
    private String secondLicensePlate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "enabled")
    private Boolean enabled;
}
