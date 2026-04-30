package com.reservia.controller;

import com.reservia.entity.LigneReservation;
import com.reservia.service.LigneReservationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lignes")
@CrossOrigin("*")
public class LigneReservationController {

    @Autowired
    private LigneReservationService ligneService;

    @PostMapping
    public LigneReservation add(@RequestBody LigneReservation ligne) {
        return ligneService.add(ligne);
    }

    @GetMapping
    public List<LigneReservation> getAll() {
        return ligneService.getAll();
    }

    @GetMapping("/{id}")
    public LigneReservation getById(@PathVariable Long id) {
        return ligneService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ligneService.delete(id);
    }
}