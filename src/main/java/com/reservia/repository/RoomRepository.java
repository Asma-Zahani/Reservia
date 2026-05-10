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
    @Query(value = "SELECT * FROM rooms WHERE id IN (:ids) FOR UPDATE", nativeQuery = true)
    List<Room> findRoomsForUpdate(@Param("ids") List<Long> ids);

    List<Room> findTop10ByOrderByIdDesc();
}