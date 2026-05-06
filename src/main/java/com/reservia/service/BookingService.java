package com.reservia.service;

import com.reservia.dto.BookingRequest;
import com.reservia.entity.*;
import com.reservia.repository.BookingRepository;
import com.reservia.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AuthService authService;
    private final RoomService roomService;

    public BookingService(BookingRepository bookingRepository, 
                          AuthService authService, 
                          RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.authService = authService;
        this.roomService = roomService;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Transactional
    public void updateBooking(Long id, BookingRequest request, Map<String,String> allParams) {
        Booking booking = getBookingById(id);
        // Exemple simple : mise à jour du status et totalPrice
        //booking.setStatus(request.getStatus());
        // Tu peux aussi mettre à jour les items si nécessaire
        bookingRepository.save(booking);
    }

    @Transactional
    public void createBooking(BookingRequest request, Map<String,String> allParams) {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
        booking.setTotalPrice(Double.valueOf(request.getTotalPrice()));
        booking.setStatus(BookingStatus.valueOf("PENDING"));
        booking.setUser(authService.getCurrentUser());

        long nights = ChronoUnit.DAYS.between(
                LocalDate.parse(request.getStartDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                LocalDate.parse(request.getEndDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        );

        List<BookingItem> items = new ArrayList<>();

        if (request.getRoomIds() != null) {
            for (Integer roomId : request.getRoomIds()) {
                int qty = allParams.get("room_" + roomId + "_qty") != null ? Integer.parseInt(allParams.get("room_" + roomId + "_qty")) : 1;

                Room room = roomService.findById(Long.valueOf(roomId));

                BookingItem item = new BookingItem();
                item.setStartDate(LocalDate.parse(request.getStartDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                item.setEndDate(LocalDate.parse(request.getEndDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                item.setQuantity(qty);
                item.setPrice((double) (room.getPrice() * qty * nights));
                item.setBooking(booking);
                item.setRoom(room);

                items.add(item);
            }
        }
        booking.setItems(items);

        bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }


    public List<Booking> getActiveBookings(User user) {
    return bookingRepository.findByUserAndStatuses(
        user,
        List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
    );
}

public List<Booking> getHistoryBookings(User user) {
    return bookingRepository.findByUserAndStatuses(
        user,
        List.of(BookingStatus.CANCELLED, BookingStatus.COMPLETED)
    );
}
}