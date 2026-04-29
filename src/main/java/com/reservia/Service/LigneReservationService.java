package com.reservia.Service;

import org.springframework.stereotype.Service;

import com.reservia.Repository.LigneReservationRepository;

@Service
public class LigneReservationService {

    private final LigneReservationRepository repository;

    public LigneReservationService(LigneReservationRepository repository) {
        this.repository = repository;
    }
}