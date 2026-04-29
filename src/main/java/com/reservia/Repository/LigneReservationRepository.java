package com.reservia.Repository;

import com.reservia.Entity.LigneReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneReservationRepository extends JpaRepository<LigneReservation, Long> {
}