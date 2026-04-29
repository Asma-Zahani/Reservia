package com.reservia.Entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Chambre extends Ressource {

    private String type; 

    private int capacite;
}