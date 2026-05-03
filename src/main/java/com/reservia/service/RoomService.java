package com.reservia.service;

import com.reservia.entities.Room;
import com.reservia.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}