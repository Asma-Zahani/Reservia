package com.reservia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reservia.entity.LigneReservation;

public interface LigneReservationRepository extends JpaRepository<LigneReservation, Long> {
}