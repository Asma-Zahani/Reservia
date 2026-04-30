package com.reservia.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LigneReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantite;
    private Double prix;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @JsonBackReference
    private Reservation reservation;
 
    @ManyToOne
    @JoinColumn(name = "chambre_id")
    private Chambre chambre;  

    @ManyToOne
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;

}