package no.bachelorgroup13.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.entity.User;
import no.bachelorgroup13.backend.service.UserService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

  @PostMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
    return userService.getUserById(id)
        .map(existingUser -> {
          user.setId(id);
          return ResponseEntity.ok(userService.updateUser(user));
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
    return userService.getUserById(id)
        .map(user -> {
          userService.deleteUser(id);
          return ResponseEntity.noContent().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());
  }
}