package com.reservia.controller;

import com.reservia.service.AuthService;
import com.reservia.repository.BookingRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

	@Autowired
	private AuthService authService;

	@Autowired
	private BookingRepository bookingRepository;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("activePage", "dashboard");

		LocalDate today = LocalDate.now();

		// Statistiques principales
		model.addAttribute("todayBookings", bookingRepository.countTodayBookings(today));
		model.addAttribute("monthRevenue", bookingRepository.revenueByMonth(today.getMonthValue(), today.getYear()));
		model.addAttribute("occupiedRooms", bookingRepository.countOccupiedRoomsToday(today));
		model.addAttribute("totalRooms", bookingRepository.count());

		// Récentes réservations (top 10)
		//model.addAttribute("recentBookings", bookingRepository.findTop10ByOrderByBookingDateDesc());

		return "pages/adminDashboard/dashboard";
	}
}