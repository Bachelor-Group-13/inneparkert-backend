package no.bachelorgroup13.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.entity.User;
import no.bachelorgroup13.backend.repository.UserRepository;
import no.bachelorgroup13.backend.security.Role;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByLicensePlate(String licensePlate) {
        Optional<User> user = userRepository.findByLicensePlate(licensePlate);
        if (user.isPresent()) {
            return user;
        }
        return userRepository.findBySecondLicensePlate(licensePlate);
    }

    public User createUser(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER);
        }
        return userRepository.save(user);
    }

    public User updateUser(User updatedUser) {
        return userRepository
                .findById(updatedUser.getId())
                .map(
                        existingUser -> {
                            if (updatedUser.getLicensePlate() != null) {
                                existingUser.setLicensePlate(updatedUser.getLicensePlate());
                            }

                            if (updatedUser.getSecondLicensePlate() != null
                                    || updatedUser.getSecondLicensePlate() == null
                                            && updatedUser.getLicensePlate() != null) {
                                existingUser.setSecondLicensePlate(
                                        updatedUser.getSecondLicensePlate());
                            }

                            if (updatedUser.getPhoneNumber() != null) {
                                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
                            }

                            if (updatedUser.getName() != null) {
                                existingUser.setName(updatedUser.getName());
                            }

                            if (updatedUser.getEmail() != null) {
                                existingUser.setEmail(updatedUser.getEmail());
                            }

                            if (updatedUser.getPassword() != null
                                    && !updatedUser.getPassword().isBlank()) {
                                existingUser.setPassword(
                                        passwordEncoder.encode(updatedUser.getPassword()));
                            } else {
                                existingUser.setPassword(updatedUser.getPassword());
                            }

                            if (updatedUser.getRole() != null) {
                                existingUser.setRole(updatedUser.getRole());
                            }

                            return userRepository.save(existingUser);
                        })
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found with id: " + updatedUser.getId()));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
