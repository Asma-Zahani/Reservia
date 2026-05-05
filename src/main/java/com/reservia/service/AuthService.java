package com.reservia.service;

import com.reservia.entity.Client;
import com.reservia.entity.Role;
import com.reservia.repository.ClientRepository;
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
import com.reservia.dto.AuthResponse;

@Service
public class AuthService {

    @Autowired
    private ClientRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void register(AuthRequest request) {
        Client client = new Client();
        client.setEmail(request.getEmail());
        client.setNom(request.getNom());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setRole(Role.valueOf("ROLE_CLIENT"));

        repo.save(client);
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

    public Client getCurrentClient() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return repo.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
        }

        throw new RuntimeException("User not authenticated");
    }


    public boolean changePassword(String currentPassword, String newPassword) {
    Client client = getCurrentClient();
    if (!passwordEncoder.matches(currentPassword, client.getPassword())) {
        return false;
    }
    client.setPassword(passwordEncoder.encode(newPassword));
    repo.save(client);

    return true;
}



    public boolean updateAccount(String nom, String email) {
        Client client = getCurrentClient();
        if (!client.getEmail().equals(email) && repo.existsByEmail(email)) {
            return false; 
        }
        client.setNom(nom);
        client.setEmail(email);
        repo.save(client);
        return true;
    }

    public boolean isEmailChanged(String newEmail) {
    Client current = getCurrentClient();
    return !current.getEmail().equals(newEmail);
}



    public void reAuthenticate(String newPassword) {
    Client client = getCurrentClient();

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    client.getEmail(),
                    newPassword
            )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
}
}