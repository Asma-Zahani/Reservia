package com.reservia.Controller;

import com.reservia.Entity.Reservation;
import com.reservia.Service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 🔹 Créer réservation
    @PostMapping
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    // 🔹 Annuler réservation
    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }
}