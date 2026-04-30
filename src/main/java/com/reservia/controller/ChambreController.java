package com.reservia.controller;

import com.reservia.entity.Chambre;
import com.reservia.service.ChambreService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/chambres")
public class ChambreController {

    private final ChambreService chambreService;

    public ChambreController(ChambreService chambreService) {
        this.chambreService = chambreService;
    }

    @PostMapping
    public Chambre add(@RequestBody Chambre chambre) {
        return chambreService.save(chambre);
    }

    @GetMapping
    public List<Chambre> getAll() {
        return chambreService.findAll();
    }

    @GetMapping("/disponibles")
    public List<Chambre> disponibles() {
        return chambreService.getDisponibles();
    }
}