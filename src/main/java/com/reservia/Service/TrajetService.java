package com.reservia.Service;

import com.reservia.Entity.Trajet;
import com.reservia.Repository.TrajetRepository;
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

    public List<Trajet> getDisponibles() {
        return trajetRepository.findByDisponibleTrue();
    }

    public List<Trajet> search(String depart, String destination) {
        return trajetRepository.findByDepartAndDestination(depart, destination);
    }
}