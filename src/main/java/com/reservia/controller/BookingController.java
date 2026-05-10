package com.reservia.controller;

import com.reservia.dto.BookingRequest;
import com.reservia.exception.RoomNotAvailableException;
import com.reservia.service.BookingService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/add")
    public String addBooking(@ModelAttribute BookingRequest bookingRequest, @RequestParam Map<String,String> allParams) {
        try {
            bookingService.createBooking(bookingRequest, allParams);
            return "redirect:/dashboard/bookings";
        } catch (RoomNotAvailableException | OptimisticLockException e) {
            return "redirect:/rooms/available?startDate="
                    + UriUtils.encode(bookingRequest.getStartDate(), StandardCharsets.UTF_8)
                    + "&endDate="
                    + UriUtils.encode(bookingRequest.getEndDate(), StandardCharsets.UTF_8);
        }
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return "redirect:/account/bookings";
    }
}