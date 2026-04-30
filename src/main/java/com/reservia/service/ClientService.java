package com.reservia.service;

import com.reservia.entity.Client;
import com.reservia.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client register(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> login(String email) {
        return clientRepository.findByEmail(email);
    }
}