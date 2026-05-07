package com.reservia.service;

import com.reservia.dto.BookingRequest;
import com.reservia.entity.*;
import com.reservia.exception.RoomNotAvailableException;
import com.reservia.repository.BookingItemRepository;
import com.reservia.repository.BookingRepository;
import com.reservia.repository.ExtraServiceRepository;
import com.reservia.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AuthService authService;
    private final RoomRepository roomRepository;
    private final ExtraServiceRepository extraServiceRepository;
    private final BookingItemRepository bookingItemRepository;

    public BookingService(BookingRepository bookingRepository, AuthService authService,
                          RoomRepository roomRepository, ExtraServiceRepository extraServiceRepository, BookingItemRepository bookingItemRepository) {
        this.bookingRepository = bookingRepository;
        this.authService = authService;
        this.roomRepository = roomRepository;
        this.extraServiceRepository = extraServiceRepository;
        this.bookingItemRepository = bookingItemRepository;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // Rollback sur exception metier personnalisee
    @Transactional(rollbackFor = RoomNotAvailableException.class)
    public void createBooking(BookingRequest request, Map<String,String> allParams) {
        LocalDate startDate = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = LocalDate.parse(request.getEndDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // Step 0: Ensure some rooms are selected
        if (request.getRoomIds() == null || request.getRoomIds().isEmpty()) {
            throw new RoomNotAvailableException("No rooms selected for booking");
        }

        // Step 1: Check room availability (WITHIN the transaction)
        List<Room> available = roomRepository.findAvailableRooms(startDate, endDate);
        List<Room> selectedRooms = available.stream()
                .filter(r -> request.getRoomIds().contains(r.getId()))
                .toList();

        // Ensure all requested rooms are available
        if (selectedRooms.size() != request.getRoomIds().size()) {
            throw new RoomNotAvailableException("One or more rooms are not available for the selected dates");
        }

        // Step 2: Calculate the number of nights
        long nights = ChronoUnit.DAYS.between(startDate, endDate);

        // Step 3: Calculate the total price of extra services
        List<Long> extraIds = request.getExtraServiceIds() != null ? request.getExtraServiceIds() : Collections.emptyList();
        List<ExtraService> extras = extraServiceRepository.findAllById(extraIds);

        double extrasPrice = extras.stream()
                .mapToDouble(e -> e.isPerNight() ? e.getPrice() * nights : e.getPrice())
                .sum();

        // Step 4: Create booking entities
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
        booking.setStatus(BookingStatus.valueOf("PENDING"));
        booking.setUser(authService.getCurrentUser());
        booking.setExtraServices(extras);

        double totalPrice = 0;

        // Create booking items for each selected room
        for (Room room : selectedRooms) {
            int qty = allParams.get("room_" + room.getId() + "_qty") != null
                    ? Integer.parseInt(allParams.get("room_" + room.getId() + "_qty"))
                    : 1;

            BookingItem item = new BookingItem();
            item.setStartDate(startDate);
            item.setEndDate(endDate);
            item.setQuantity(qty);
            item.setPrice(room.getPrice() * qty * nights);
            item.setRoom(room);
            item.setBooking(booking);

            booking.getItems().add(item);

            totalPrice += item.getPrice();
        }

        // Set the final total price including extras
        booking.setTotalPrice(totalPrice + extrasPrice);

        // Persist booking (atomic transaction)
        bookingRepository.save(booking);
    }

    public void updateBooking(Long id, BookingRequest request, Map<String,String> allParams) {
        Booking booking = getBookingById(id);
        // Exemple simple : mise à jour du status et totalPrice
        //booking.setStatus(request.getStatus());
        // Tu peux aussi mettre à jour les items si nécessaire
        bookingRepository.save(booking);
    }

    @Transactional  // Commit si tout OK, Rollback automatique si exception -> Hibernate detecte le changement et fait UPDATE
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getId().equals(authService.getCurrentUser().getId())) {
            throw new AccessDeniedException("You are not the owner of this booking");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("This booking has already been cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public List<String> getDisabledDatesForRoom(Long roomId) {
        List<BookingItem> items = bookingItemRepository.findActiveBookingItemsByRoom(roomId);
        List<String> disabledDates = new ArrayList<>();

        for (var item : items) {
            LocalDate start = item.getStartDate();
            LocalDate end = item.getEndDate();

            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                disabledDates.add(date.toString()); // format ISO yyyy-MM-dd
            }
        }

        return disabledDates;
    }

    public Page<Booking> getActiveBookings(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findByUserAndStatuses(user, List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED), pageable);
    }


    public Page<Booking> getHistoryBookings(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findByUserAndStatuses(user, List.of(BookingStatus.CANCELLED, BookingStatus.COMPLETED), pageable);
    }
}