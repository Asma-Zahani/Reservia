package com.reservia.repository;

import com.reservia.entity.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {
    // JPQL pour récupérer toutes les dates réservées pour une chambre donnée
    @Query("SELECT b.startDate FROM BookingItem b " +
            "WHERE b.room.id = :roomId AND b.booking.status != 'CANCELLED'")
    List<LocalDate> findBookedStartDatesByRoom(@Param("roomId") Long roomId);

    // Récupérer toutes les dates entre start et end
    @Query("SELECT b FROM BookingItem b " +
            "WHERE b.room.id = :roomId AND b.booking.status != 'CANCELLED'")
    List<BookingItem> findActiveBookingItemsByRoom(@Param("roomId") Long roomId);

    @Query("""
        SELECT COALESCE(SUM(bi.quantity), 0)
        FROM BookingItem bi
        WHERE bi.room.id = :roomId
        AND bi.booking.status <> 'CANCELLED'
        AND bi.startDate < :endDate
        AND bi.endDate > :startDate
    """)
    Integer getReservedQuantityBetweenDates(
            Long roomId,
            LocalDate startDate,
            LocalDate endDate
    );
}