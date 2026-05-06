package com.reservia.repository;

import com.reservia.entity.Booking;
import com.reservia.entity.BookingStatus;
import com.reservia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
    SELECT b FROM Booking b
    WHERE b.user = :user
    AND b.status IN :statuses
""")
List<Booking> findByUserAndStatuses(
    @Param("user") User user,
    @Param("statuses") List<BookingStatus> statuses
);


    List<Booking> findByUser(User user);
}