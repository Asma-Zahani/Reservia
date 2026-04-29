package com.reservia.Service;

import com.reservia.Entity.Client;
import com.reservia.Repository.ClientRepository;
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