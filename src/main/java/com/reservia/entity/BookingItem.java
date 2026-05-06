package com.reservia.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    @Transient
    private Long duration;

    public Long getDuration() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.DAYS.between(startDate, endDate);
        }
        return 0L;
    }
}
