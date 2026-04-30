package com.reservia.repository;

import com.reservia.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrajetRepository extends JpaRepository<Trajet, Long> {

    List<Trajet> findByDepartAndDestination(String depart, String destination);

    List<Trajet> findByDisponibleTrue();
}