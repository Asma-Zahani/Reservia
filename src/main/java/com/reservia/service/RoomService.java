package com.reservia.service;

import com.reservia.entity.Room;
import com.reservia.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Page<Room> getRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return roomRepository.findAll(pageable);
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> findSimilarRooms(String type, Long excludeId) {
        return roomRepository.findAll()
                .stream()
                .filter(r -> !r.getId().equals(excludeId))
                .filter(r -> r.getType().equals(type))
                .limit(3)
                .toList();
    }

        public void saveRoom(Room room) {
            roomRepository.save(room);
        }

        public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Room> getAvailableRooms(LocalDate start, LocalDate end) {
        return roomRepository.findAvailableRooms(start, end);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}