package no.bachelorgroup13.backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.dto.JwtResponse;
import no.bachelorgroup13.backend.dto.LoginRequest;
import no.bachelorgroup13.backend.dto.MessageResponse;
import no.bachelorgroup13.backend.dto.SignupRequest;
import no.bachelorgroup13.backend.entity.User;
import no.bachelorgroup13.backend.repository.UserRepository;
import no.bachelorgroup13.backend.security.CustomUserDetails;
import no.bachelorgroup13.backend.security.JwtTokenProvider;
import no.bachelorgroup13.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        origins = {"http://localhost:3000", "http://129.241.152.242:8081"},
        allowCredentials = "true")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/check")
    public ResponseEntity<Boolean> checkPasswords(@RequestBody Map<String, String> passwords) {
        String raw = passwords.get("password");
        String hashed = passwords.get("hashedPassword");

        System.out.println("Raw: " + raw);
        System.out.println("Hashed: " + hashed);
        System.out.println("Password matches: " + passwordEncoder.matches(raw, hashed));
        return ResponseEntity.ok(
                passwordEncoder.matches(
                        passwords.get("password"), passwords.get("hashedPassword")));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        Cookie jwtCookie = new Cookie("user", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(86400);
        jwtCookie.setSecure(false);
        jwtCookie.setDomain("localhost");
        response.addCookie(jwtCookie);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user =
                userRepository
                        .findByEmail(userDetails.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        "Bearer",
                        userDetails.getId(),
                        userDetails.getUsername(),
                        user.getName(),
                        null));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(
            @Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(HttpServletRequest request) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAccessToken = authService.refreshToken(refreshToken);
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        User user =
                userRepository
                        .findByEmail(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

        String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);

        return ResponseEntity.ok(
                new JwtResponse(
                        newAccessToken,
                        "Bearer",
                        user.getId(),
                        username,
                        user.getName(),
                        newRefreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("user", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        jwtCookie.setSecure(false);
        response.addCookie(jwtCookie);
        return ResponseEntity.ok().build();
    }
}
