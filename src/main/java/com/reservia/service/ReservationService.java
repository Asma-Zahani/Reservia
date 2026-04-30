package com.reservia.service;

import com.reservia.entity.*;
import com.reservia.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ChambreRepository chambreRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ChambreRepository chambreRepository) {
        this.reservationRepository = reservationRepository;
        this.chambreRepository = chambreRepository;
    }

    // ✅ Créer réservation (UNIQUEMENT CHAMBRE)
    @Transactional
    public Reservation createReservation(Reservation reservation) {

        double total = 0;

        for (LigneReservation ligne : reservation.getLignes()) {

            Long idChambre = ligne.getRessource().getId();

            Chambre chambre = chambreRepository.findById(idChambre)
                    .orElseThrow(() -> new RuntimeException("Chambre introuvable"));

            if (!chambre.isDisponible()) {
                throw new RuntimeException("Chambre non disponible !");
            }

            double prix = chambre.getPrix() * ligne.getQuantite();
            ligne.setPrix(prix);
            total += prix;

            chambre.setDisponible(false);
        }

        reservation.setDateReservation(LocalDate.now());
        reservation.setTotalPrix(total);

        return reservationRepository.save(reservation);
    }

    // ❌ Annuler réservation
    @Transactional
    public void cancelReservation(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation non trouvée"));

        for (LigneReservation ligne : reservation.getLignes()) {

            Chambre chambre = (Chambre) ligne.getRessource();
            chambre.setDisponible(true);
        }

        reservationRepository.delete(reservation);
    }
}