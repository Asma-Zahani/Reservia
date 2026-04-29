package com.reservia.Controller;

import com.reservia.Entity.Client;
import com.reservia.Service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // 🔹 Inscription
    @PostMapping("/register")
    public Client register(@RequestBody Client client) {
        return clientService.register(client);
    }

    // 🔹 Login simple
    @GetMapping("/login")
    public Client login(@RequestParam String email) {
        return clientService.login(email)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
    }
}