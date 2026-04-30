package com.reservia.repository;

import com.reservia.entity.LigneReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneReservationRepository extends JpaRepository<LigneReservation, Long> {
}