package no.bachelorgroup13.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.dto.JwtResponse;
import no.bachelorgroup13.backend.dto.LoginRequest;
import no.bachelorgroup13.backend.dto.MessageResponse;
import no.bachelorgroup13.backend.dto.SignupRequest;
import no.bachelorgroup13.backend.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authService.authenticateUser(loginRequest));
  }

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    return ResponseEntity.ok(authService.registerUser(signUpRequest));
  }
}
