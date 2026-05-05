package com.reservia.repository;

import com.reservia.entity.Room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("""
    SELECT r FROM Room r
    WHERE r.id NOT IN (
        SELECT bi.room.id FROM BookingItem bi
        WHERE (:startDate <= bi.endDate)
        AND (:endDate >= bi.startDate)
    )
    """)
    List<Room> findAvailableRooms(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}