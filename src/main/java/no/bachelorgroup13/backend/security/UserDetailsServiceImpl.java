package no.bachelorgroup13.backend.security;

import lombok.RequiredArgsConstructor;
import no.bachelorgroup13.backend.entity.User;
import no.bachelorgroup13.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
        private final UserRepository userRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = userRepository
                                .findByEmail(email)
                                .orElseThrow(
                                                () -> new UsernameNotFoundException(
                                                                "User not found with email: " + email));

                return new CustomUserDetails(
                                user.getId(),
                                user.getEmail(),
                                user.getPassword(),
                                user.getEnabled(),
                                user.getRole());
        }
}
