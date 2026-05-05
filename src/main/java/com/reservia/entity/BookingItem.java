package com.reservia.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer quantity;
    private Double price;

    @ManyToOne
    private Booking booking;

    @ManyToOne
    private Room room;
}
