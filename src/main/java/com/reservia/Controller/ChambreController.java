package com.reservia.Controller;

import com.reservia.Entity.Chambre;
import com.reservia.Service.ChambreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chambres")
public class ChambreController {

    private final ChambreService chambreService;

    public ChambreController(ChambreService chambreService) {
        this.chambreService = chambreService;
    }

    // 🔹 Ajouter chambre
    @PostMapping
    public Chambre add(@RequestBody Chambre chambre) {
        return chambreService.save(chambre);
    }

    // 🔹 Liste disponibles
    @GetMapping("/disponibles")
    public List<Chambre> disponibles() {
        return chambreService.getDisponibles();
    }
}