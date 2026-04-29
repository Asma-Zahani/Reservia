package com.reservia.Service;

import com.reservia.Entity.Chambre;
import com.reservia.Repository.ChambreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChambreService {

    private final ChambreRepository chambreRepository;

    public ChambreService(ChambreRepository chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    public List<Chambre> getDisponibles() {
        return chambreRepository.findByDisponibleTrue();
    }

    public Chambre save(Chambre c) {
        return chambreRepository.save(c);
    }
}