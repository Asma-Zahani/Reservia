package com.reservia.Repository;

import com.reservia.Entity.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    List<Chambre> findByDisponibleTrue();
}