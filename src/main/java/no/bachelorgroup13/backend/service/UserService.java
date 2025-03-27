package no.bachelorgroup13.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.entity.User;
import no.bachelorgroup13.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

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
    return userRepository.save(user);
  }

  public User updateUser(User user) {
    return userRepository.save(user);
  }

  public void deleteUser(UUID id) {
    userRepository.deleteById(id);
  }
}
