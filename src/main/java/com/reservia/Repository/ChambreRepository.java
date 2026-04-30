package com.reservia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reservia.entity.Chambre;
import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    List<Chambre> findByDisponibleTrue();

}