package com.reservia.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean disponible = true;
    private Double prix;
    private String type; 
    private Integer capacite;

    public Chambre(boolean disponible, double prix, String type, int capacite) {
        this.disponible = disponible;
        this.prix = prix;
        this.type = type;
        this.capacite = capacite;
    }

    @OneToMany(mappedBy = "chambre")
    @JsonIgnore
    private List<LigneReservation> lignes;

}