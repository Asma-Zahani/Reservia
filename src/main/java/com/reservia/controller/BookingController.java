package com.reservia.controller;

import com.reservia.dto.BookingRequest;
import com.reservia.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking/add")
    public String addBooking(@ModelAttribute BookingRequest bookingRequest, @RequestParam Map<String,String> allParams) {
        bookingService.createBooking(bookingRequest, allParams);

        return "redirect:/dashboard/bookings";
    }
}