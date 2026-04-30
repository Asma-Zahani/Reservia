package com.reservia.service;

import org.springframework.stereotype.Service;

import com.reservia.repository.LigneReservationRepository;

@Service
public class LigneReservationService {

    private final LigneReservationRepository repository;

    public LigneReservationService(LigneReservationRepository repository) {
        this.repository = repository;
    }
}