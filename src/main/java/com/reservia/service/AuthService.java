package com.reservia.service;

import com.reservia.entity.Client;
import com.reservia.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reservia.config.JwtService;
import com.reservia.dto.AuthRequest;
import com.reservia.dto.AuthResponse;
import com.reservia.entity.Role;
import com.reservia.entity.User;
import com.reservia.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private ClientRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    public AuthResponse register(AuthRequest request) {

        Client client = new Client();
        client.setEmail(request.getEmail());
        client.setPassword(encoder.encode(request.getPassword()));
        client.setNom(request.getNom());
        repo.save(client);

        String token = jwtService.generateToken(client.getEmail());
        return new AuthResponse(token, client);
    }

    public AuthResponse login(AuthRequest request) {

        Client client = repo.findByEmail(request.getEmail())
                .orElseThrow();

        if (!encoder.matches(request.getPassword(), client.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(client.getEmail());
        return new AuthResponse(token, client);
    }
}