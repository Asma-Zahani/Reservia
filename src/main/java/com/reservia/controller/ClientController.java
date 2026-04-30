package com.reservia.controller;

import com.reservia.entity.Client;
import com.reservia.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


}