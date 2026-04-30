package com.reservia.service;

import com.reservia.entity.Trajet;
import com.reservia.repository.TrajetRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrajetService {

    private final TrajetRepository trajetRepository;

    public TrajetService(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    public Trajet save(Trajet trajet) {
        return trajetRepository.save(trajet);
    }

    public List<Trajet> search(String depart, String destination) {
        return trajetRepository.findByDepartAndDestination(depart, destination);
    }

    public List<Trajet> getAll() {
        return trajetRepository.findAll();
    }
}