package no.bachelorgroup13.backend.features.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.features.auth.security.CustomUserDetails;
import no.bachelorgroup13.backend.features.user.dto.UserDto;
import no.bachelorgroup13.backend.features.user.entity.User;
import no.bachelorgroup13.backend.features.user.mapper.UserMapper;
import no.bachelorgroup13.backend.features.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints for managing users.")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    @Autowired private PasswordEncoder passwordEncoder;

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers().stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toList()));
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        return userService
                .getUserById(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user by license plate")
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

    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(createdUser));
    }

    @Operation(summary = "Update user by ID")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        return userService
                .getUserById(id)
                .map(
                        existingUser -> {
                            User user = userMapper.toEntity(userDto);
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

                            user.setRole(existingUser.getRole());

                            User updatedUser = userService.updateUser(user);
                            return ResponseEntity.ok(userMapper.toDto(updatedUser));
                        })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete user by ID")
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

    @Operation(summary = "Get current user")
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null
                || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService
                .getUserById(userDetails.getId())
                .map(
                        user -> {
                            UserDto userDto = new UserDto();
                            userDto.setId(user.getId());
                            userDto.setName(user.getName());
                            userDto.setEmail(user.getEmail());
                            userDto.setPhoneNumber(user.getPhoneNumber());
                            userDto.setLicensePlate(user.getLicensePlate());
                            userDto.setSecondLicensePlate(user.getSecondLicensePlate());
                            userDto.setRole(user.getRole());
                            return ResponseEntity.ok(userDto);
                        })
                .orElse(ResponseEntity.notFound().build());
    }
}
