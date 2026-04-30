package com.reservia.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Trajet extends Ressource {

    private String depart;

    private String destination;

    private String dateDepart;

    private int placesDisponibles;
}