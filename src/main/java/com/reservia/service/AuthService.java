package com.reservia.service;

import com.reservia.entity.Role;
import com.reservia.entity.User;
import com.reservia.entity.VerificationToken;
import com.reservia.repository.TokenRepository;
import com.reservia.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.reservia.dto.AuthRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenRepository tokenRepository;

    public void register(AuthRequest request) throws MessagingException {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getNom());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf("ROLE_USER"));

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        VerificationToken vt = new VerificationToken();
        vt.setToken(token);
        vt.setUser(user);
        vt.setExpiryDate(LocalDateTime.now().plusHours(24));

        tokenRepository.save(vt);
        emailService.sendVerificationEmail(user.getEmail(), token);
    }

    public void login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isEnabled()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            throw new RuntimeException("Please verify your email before logging in.");
        }

    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        throw new RuntimeException("User not authenticated");
    }

    public boolean changePassword(String currentPassword, String newPassword) {
        User user = getCurrentUser();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

    public boolean updateAccount(String nom, String email) {
        User user = getCurrentUser();
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            return false; 
        }
        user.setName(nom);
        user.setEmail(email);
        userRepository.save(user);
        return true;
    }

    public boolean isEmailChanged(String newEmail) {
        User current = getCurrentUser();
        return !current.getEmail().equals(newEmail);
    }
}