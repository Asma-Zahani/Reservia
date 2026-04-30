package com.reservia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reservia.entity.LigneReservation;
import com.reservia.repository.LigneReservationRepository;

@Service
public class LigneReservationService {

    @Autowired
    private LigneReservationRepository ligneRepository;

    public LigneReservation add(LigneReservation ligne) {
        return ligneRepository.save(ligne);
    }

    public List<LigneReservation> getAll() {
        return ligneRepository.findAll();
    }

    public LigneReservation getById(Long id) {
        return ligneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LigneReservation introuvable"));
    }

    public void delete(Long id) {
        ligneRepository.deleteById(id);
    }
}