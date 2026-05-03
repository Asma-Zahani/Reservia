package com.reservia.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image_path;

    private String type;

    private int size;

    private int capacity;

    private int price;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
}