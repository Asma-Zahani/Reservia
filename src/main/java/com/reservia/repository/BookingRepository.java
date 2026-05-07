package com.reservia.repository;

import com.reservia.entity.Booking;
import com.reservia.entity.BookingStatus;
import com.reservia.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
        SELECT b FROM Booking b
        WHERE b.user = :user
        AND b.status IN :statuses
    """)
    Page<Booking> findByUserAndStatuses(@Param("user") User user, @Param("statuses") List<BookingStatus> statuses, Pageable pageable);


    // Reservations du jour
    @Query("""
        SELECT COUNT(b) FROM Booking b
        WHERE b.bookingDate = :today
        AND b.status = 'CONFIRMED'
    """)
    long countTodayBookings(@Param("today") LocalDate today);

    // Revenus du mois
    @Query("""
        SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b
        WHERE MONTH(b.bookingDate) = :month
        AND YEAR(b.bookingDate) = :year
        AND b.status = 'CONFIRMED'
    """)
    Double revenueByMonth(@Param("month") int month, @Param("year") int year);

    // Chambres occupees aujourd'hui
    @Query("""
        SELECT COUNT(DISTINCT bi.room.id) FROM BookingItem bi
        WHERE bi.startDate <= :today AND bi.endDate > :today
        AND bi.booking.status = 'CONFIRMED'
    """)
    long countOccupiedRoomsToday(@Param("today") LocalDate today);
}