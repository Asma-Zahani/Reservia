package com.reservia.controller;

import com.reservia.dto.BookingRequest;
import com.reservia.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        bookingService.createBooking(bookingRequest, allParams);

        return "redirect:/dashboard/bookings";
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return "redirect:/dashboard/bookings";
    }
}