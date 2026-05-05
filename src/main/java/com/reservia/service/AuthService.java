package com.reservia.service;

import com.reservia.entity.Role;
import com.reservia.entity.User;
import com.reservia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reservia.config.JwtService;
import com.reservia.dto.AuthRequest;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void register(AuthRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getNom());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf("ROLE_USER"));

        repo.save(user);
    }

    public void login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return repo.findByEmail(userDetails.getUsername())
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
    repo.save(user);

    return true;
}



    public boolean updateAccount(String nom, String email) {
        User user = getCurrentUser();
        if (!user.getEmail().equals(email) && repo.existsByEmail(email)) {
            return false; 
        }
        user.setName(nom);
        user.setEmail(email);
        repo.save(user);
        return true;
    }

    public boolean isEmailChanged(String newEmail) {
        User current = getCurrentUser();
        return !current.getEmail().equals(newEmail);
    }

    public void reAuthenticate(String newPassword) {
    User user = getCurrentUser();

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    newPassword
            )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
}
}