package no.bachelorgroup13.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.dto.UserDto;
import no.bachelorgroup13.backend.entity.User;
import no.bachelorgroup13.backend.security.CustomUserDetails;
import no.bachelorgroup13.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return userService
                .getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/license-plate/{licensePlate}")
    public ResponseEntity<?> getUserByLicensePlate(@PathVariable String licensePlate) {
        System.out.println("License plate lookup request received for: " + licensePlate);

        Optional<User> userOptional = userService.getUserByLicensePlate(licensePlate);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user);

            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setLicensePlate(user.getLicensePlate());
            userDto.setSecondLicensePlate(user.getSecondLicensePlate());
            return ResponseEntity.ok(userDto);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("found", String.valueOf(false));
            response.put("licensePlate", licensePlate);
            response.put("message", "No user found with license plate: ");
            System.out.println("No user found with license plate: " + licensePlate);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        return userService
                .getUserById(id)
                .map(
                        existingUser -> {
                            user.setId(id);

                            if (user.getEmail() == null) {
                                user.setEmail(existingUser.getEmail());
                            }
                            if (user.getName() == null) {
                                user.setName(existingUser.getName());
                            }
                            if (user.getPassword() == null || user.getPassword().isBlank()) {
                                user.setPassword(existingUser.getPassword());
                            } else {
                                user.setPassword(passwordEncoder.encode(user.getPassword()));
                            }

                            return ResponseEntity.ok(userService.updateUser(user));
                        })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        return userService
                .getUserById(id)
                .map(
                        user -> {
                            userService.deleteUser(id);
                            return ResponseEntity.noContent().<Void>build();
                        })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null
                || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService
                .getUserById(userDetails.getId())
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setName(user.getName());
                    userDto.setEmail(user.getEmail());
                    userDto.setPhoneNumber(user.getPhoneNumber());
                    userDto.setLicensePlate(user.getLicensePlate());
                    userDto.setSecondLicensePlate(user.getSecondLicensePlate());
                    return ResponseEntity.ok(userDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
