package com.reservia.service;

import com.reservia.dto.BookingRequest;
import com.reservia.entity.*;
import com.reservia.exception.RoomNotAvailableException;
import com.reservia.repository.BookingItemRepository;
import com.reservia.repository.BookingRepository;
import com.reservia.repository.ExtraServiceRepository;
import com.reservia.repository.RoomRepository;
import jakarta.mail.MessagingException;
import org.jspecify.annotations.Nullable;
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
import java.util.*;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AuthService authService;
    private final RoomRepository roomRepository;
    private final ExtraServiceRepository extraServiceRepository;
    private final BookingItemRepository bookingItemRepository;
    private final EmailService emailService;

    public BookingService(BookingRepository bookingRepository, AuthService authService,
                          RoomRepository roomRepository, ExtraServiceRepository extraServiceRepository, BookingItemRepository bookingItemRepository, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.authService = authService;
        this.roomRepository = roomRepository;
        this.extraServiceRepository = extraServiceRepository;
        this.bookingItemRepository = bookingItemRepository;
        this.emailService = emailService;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // Rollback sur exception metier personnalisee
    @Transactional(rollbackFor = RoomNotAvailableException.class)
    public void createBooking(BookingRequest request, Map<String, String> allParams) {
        // Step 0: Parse the dates from the request
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(request.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(request.getEndDate(), formatter);

        // Step 1: Ensure at least one room is selected
        if (request.getRoomIds() == null || request.getRoomIds().isEmpty()) {
            throw new RoomNotAvailableException("No rooms selected for booking");
        }

        // Step 2: Lock the selected rooms in the database to prevent overbooking
        List<Room> roomsToBook = roomRepository.findRoomsForUpdate(request.getRoomIds());

        if (roomsToBook.size() != request.getRoomIds().size()) {
            throw new RoomNotAvailableException("One or more selected rooms do not exist");
        }

        // Step 3: Retrieve all active BookingItems that overlap the requested period
        List<BookingItem> items = bookingItemRepository.findActiveBookingItemsForPeriod(startDate, endDate);

        // Step 4: Build a Map<RoomId, Map<Date, bookedQuantity>>
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

        // Step 5: Check availability per room per day
        for (Room room : roomsToBook) {
            int requestedQty = allParams.get("room_" + room.getId() + "_qty") != null
                    ? Integer.parseInt(allParams.get("room_" + room.getId() + "_qty"))
                    : 1;

            Map<LocalDate, Integer> roomBooked = bookedPerRoom.getOrDefault(room.getId(), new HashMap<>());

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                int bookedQty = roomBooked.getOrDefault(date, 0);
                if (bookedQty + requestedQty > room.getTotalQuantity()) {
                    throw new RoomNotAvailableException(
                            "Room " + room.getType() + " is not available for the selected dates and quantity"
                    );
                }
            }
        }

        // Step 6: Calculate the number of nights
        long nights = ChronoUnit.DAYS.between(startDate, endDate);

        // Step 7: Retrieve extras and calculate their price
        List<Long> extraIds = request.getExtraServiceIds() != null ? request.getExtraServiceIds() : Collections.emptyList();
        List<ExtraService> extras = extraServiceRepository.findAllById(extraIds);

        double extrasPrice = extras.stream()
                .mapToDouble(e -> e.isPerNight() ? e.getPrice() * nights : e.getPrice())
                .sum();

        // Step 8: Create the booking entity
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
        booking.setStatus(BookingStatus.PENDING);
        booking.setUser(authService.getCurrentUser());
        booking.setExtraServices(extras);

        double totalPrice = 0;

        // Step 9: Create BookingItems for each selected room
        for (Room room : roomsToBook) {
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

        // Step 10: Add extras price to the total
        booking.setTotalPrice(totalPrice + extrasPrice);

        // Step 11: Save the booking (atomic transaction)
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
        int totalQuantity = roomRepository.findById(roomId).orElseThrow().getTotalQuantity();

        List<LocalDate> allBookedDates = new ArrayList<>();
        // Ajouter chaque date des réservations dans la liste, répétée selon la quantité
        for (BookingItem item : items) {
            LocalDate start = item.getStartDate();
            LocalDate end = item.getEndDate();
            int qty = item.getQuantity();

            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                for (int i = 0; i < qty; i++) {
                    allBookedDates.add(date);
                }
            }
        }
        List<String> disabledDates = allBookedDates.stream()
                .distinct()
                .filter(date -> allBookedDates.stream().filter(d -> d.equals(date)).count() >= totalQuantity)
                .map(LocalDate::toString)
                .toList();
        return disabledDates;
    }


        public Page<Booking> getActiveBookings(User user, int page, int size) {
            Pageable pageable = PageRequest.of(page, size);
            return bookingRepository.findByUserAndStatuses(
                user,
                List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED, BookingStatus.PAID),
                pageable
            );
        }



    public Page<Booking> getHistoryBookings(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findByUserAndStatuses(user, List.of(BookingStatus.CANCELLED, BookingStatus.COMPLETED), pageable);
    }


    public Page<Booking> getAllBookings(int page, int size) {

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by("bookingDate").descending()
    );

    return bookingRepository.findAll(pageable);
}

    public void updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(status);
        bookingRepository.save(booking);

        if (status == BookingStatus.CONFIRMED) {
            try {
                emailService.sendBookingVerifiedEmail(booking.getUser().getEmail(), "#" + booking.getId());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Integer getReservedQuantityBetweenDates(Long roomId, LocalDate startDate, LocalDate endDate) {
        return bookingItemRepository.getReservedQuantityBetweenDates(roomId, startDate, endDate);
    }
}