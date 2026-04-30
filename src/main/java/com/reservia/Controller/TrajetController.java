package com.reservia.controller;

import com.reservia.entity.Trajet;
import com.reservia.service.TrajetService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/trajets")
public class TrajetController {

    private final TrajetService trajetService;

    public TrajetController(TrajetService trajetService) {
        this.trajetService = trajetService;
    }

    @PostMapping
    public Trajet create(@RequestBody Trajet trajet) {
        return trajetService.save(trajet);
    }

    @GetMapping("/search")
    public List<Trajet> search(@RequestParam String depart,
                               @RequestParam String destination) {
        return trajetService.search(depart, destination);
    }

    @GetMapping
    public List<Trajet> getAll() {
        return trajetService.getAll();
    }
}