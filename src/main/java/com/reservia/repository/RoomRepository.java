package com.reservia.repository;

import com.reservia.entity.Room;

import jakarta.persistence.LockModeType;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // Recherche des chambres disponibles
    @Query("""
        SELECT r FROM Room r
        WHERE r.id NOT IN (
            SELECT bi.room.id FROM BookingItem bi
            WHERE (:startDate <= bi.endDate)
            AND (:endDate >= bi.startDate)
            AND (
                bi.booking.status = 'CONFIRMED'
                OR bi.booking.status = 'PENDING'
            )
        )
        ORDER BY r.price ASC
    """)
    List<Room> findAvailableRooms(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Vérification pendant réservation
    @Query("""
        SELECT r FROM Room r
        WHERE r.id IN :roomIds
        AND r.id NOT IN (
            SELECT bi.room.id FROM BookingItem bi
            WHERE (:startDate <= bi.endDate)
            AND (:endDate >= bi.startDate)
            AND (
                bi.booking.status = 'CONFIRMED'
                OR bi.booking.status = 'PENDING'
            )
        )
    """)
    List<Room> findAvailableRoomsForBooking(
            @Param("roomIds") List<Long> roomIds,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}