package com.reservia.service;

import com.reservia.entity.BookingItem;
import com.reservia.entity.Room;
import com.reservia.repository.BookingItemRepository;
import com.reservia.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingItemRepository bookingItemRepository;

    public RoomService(RoomRepository roomRepository, BookingItemRepository bookingItemRepository) {
        this.roomRepository = roomRepository;
        this.bookingItemRepository = bookingItemRepository;
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
    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        List<Room> allRooms = roomRepository.findAll();
        List<BookingItem> items = bookingItemRepository.findActiveBookingItemsForPeriod(startDate, endDate);

        // Map<RoomId, Map<Date, totalBookedQuantity>>
        Map<Long, Map<LocalDate, Integer>> bookedPerRoom = new HashMap<>();

        for (BookingItem item : items) {
            Long roomId = item.getRoom().getId();
            bookedPerRoom.putIfAbsent(roomId, new HashMap<>());

            LocalDate itemStart = item.getStartDate();
            LocalDate itemEnd = item.getEndDate();
            int qty = item.getQuantity();

            for (LocalDate date = itemStart; !date.isAfter(itemEnd); date = date.plusDays(1)) {
                bookedPerRoom.get(roomId).merge(date, qty, Integer::sum);
            }
        }

        List<Room> availableRooms = new ArrayList<>();

        for (Room room : allRooms) {
            boolean isAvailable = true;
            Map<LocalDate, Integer> roomBooked = bookedPerRoom.getOrDefault(room.getId(), new HashMap<>());

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                int bookedQty = roomBooked.getOrDefault(date, 0);
                if (bookedQty >= room.getTotalQuantity()) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}