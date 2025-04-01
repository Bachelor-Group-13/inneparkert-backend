package no.bachelorgroup13.backend.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import no.bachelorgroup13.backend.dto.JwtResponse;
import no.bachelorgroup13.backend.dto.LoginRequest;
import no.bachelorgroup13.backend.dto.MessageResponse;
import no.bachelorgroup13.backend.dto.SignupRequest;
import no.bachelorgroup13.backend.entity.User;
import no.bachelorgroup13.backend.repository.UserRepository;
import no.bachelorgroup13.backend.security.JwtTokenProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final JwtTokenProvider jwtTokenProvider;
  private static final Logger log = LoggerFactory.getLogger(AuthService.class);

  public JwtResponse authenticateUser(LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtTokenProvider.generateToken(authentication);
      String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequest.getEmail());

      User user = userRepository.findByEmail(loginRequest.getEmail())
          .orElseThrow(() -> new RuntimeException("User not found"));

      return new JwtResponse(jwt, "Bearer", user.getId(), user.getEmail(), user.getName(), refreshToken);
    } catch (Exception e) {
      log.error("Authentication failed: {}", e.getMessage(), e);
      throw e;
    }

  }

  public MessageResponse registerUser(SignupRequest signUpRequest) {
    if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
      return new MessageResponse("Error: Email is already in use!");
    }

    // Create new user's account
    User user = new User();
    user.setId(UUID.randomUUID());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));
    user.setName(signUpRequest.getName());
    user.setLicensePlate(
        signUpRequest.getLicensePlate() != null ? signUpRequest.getLicensePlate().toUpperCase() : null);
    user.setPhoneNumber(signUpRequest.getPhoneNumber());
    user.setEnabled(true);

    userRepository.save(user);

    return new MessageResponse("User registered successfully!");
  }

  public String refreshToken(String refreshToken) {
    if (jwtTokenProvider.validateToken(refreshToken)) {
      String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
      return jwtTokenProvider.generateTokenWithUsername(username);
    }
    throw new RuntimeException("Invalid refresh token");
  }
}
