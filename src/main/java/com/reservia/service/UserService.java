package com.reservia.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.reservia.entity.Role;
import com.reservia.entity.User;
import com.reservia.entity.VerificationToken;
import com.reservia.repository.TokenRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reservia.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Page<User> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByRole(Role.valueOf("ROLE_USER"), pageable);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole().name().equals("ROLE_ADMIN")) {
            throw new RuntimeException("Cannot delete admin user");
        }

        userRepository.delete(user);
    }

    public void verifyUser(String token) {
        VerificationToken vt = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));

        if (vt.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = vt.getUser();
        user.setEnabled(true);

        userRepository.save(user);
        tokenRepository.delete(vt); // bonus sécurité

        try {
            emailService.sendWelcomeEmail(user.getEmail(), user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

