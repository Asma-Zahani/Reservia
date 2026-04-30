package com.reservia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservia.entity.Chambre;
import com.reservia.entity.Client;
import com.reservia.entity.LigneReservation;
import com.reservia.entity.Reservation;
import com.reservia.entity.Trajet;
import com.reservia.repository.ChambreRepository;
import com.reservia.repository.ClientRepository;
import com.reservia.repository.ReservationRepository;
import com.reservia.repository.TrajetRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Reservation createReservation(Reservation reservation) {

        Client client = clientRepository.findById(reservation.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        reservation.setClient(client);

        reservation.setDateReservation(LocalDate.now());

        double total = 0;

        for (LigneReservation ligne : reservation.getLignes()) {

            ligne.setReservation(reservation);

            if (ligne.getChambre() != null) {

                Chambre ch = chambreRepository.findById(ligne.getChambre().getId())
                        .orElseThrow(() -> new RuntimeException("Chambre introuvable"));

                ligne.setChambre(ch);

                ligne.setPrix(ch.getPrix());
            }

            if (ligne.getTrajet() != null) {

                Trajet tr = trajetRepository.findById(ligne.getTrajet().getId())
                        .orElseThrow(() -> new RuntimeException("Trajet introuvable"));

                ligne.setTrajet(tr);

                ligne.setPrix(tr.getPrix());
            }

            total += ligne.getPrix() * ligne.getQuantite();
        }

        reservation.setTotalPrix(total);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation introuvable"));
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}