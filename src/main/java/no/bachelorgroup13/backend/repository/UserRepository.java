package no.bachelorgroup13.backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.bachelorgroup13.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByLicensePlate(String licensePlate);

  Optional<User> findBySecondLicensePlate(String secondLicensePlate);

  Optional<User> findByEmail(String email);
}
