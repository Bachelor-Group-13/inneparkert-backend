package no.bachelorgroup13.backend.controller;

import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.entity.User;
import no.bachelorgroup13.backend.security.CustomUserDetails;
import no.bachelorgroup13.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable UUID id) {
    return userService.getUserById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/license-plate/{licensePlate}")
  public ResponseEntity<User> getUserByLicensePlate(@PathVariable String licensePlate) {
    return userService.getUserByLicensePlate(licensePlate)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userService.createUser(user));
  }

  @PutMapping("/{id}") // Changed from PostMapping to PutMapping
  public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
    return userService.getUserById(id)
        .map(existingUser -> {
          user.setId(id);
          return ResponseEntity.ok(userService.updateUser(user));
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}") // Changed from PostMapping to DeleteMapping
  public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
    return userService.getUserById(id)
        .map(user -> {
          userService.deleteUser(id);
          return ResponseEntity.noContent().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/me")
  public ResponseEntity<User> getCurrentUser(Authentication authentication) {
    if (authentication == null ||
        !(authentication.getPrincipal() instanceof CustomUserDetails)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    return userService.getUserById(userDetails.getId())
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
