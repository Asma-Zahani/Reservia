package com.reservia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reservia.entity.Reservation;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClientId(Long clientId);
}