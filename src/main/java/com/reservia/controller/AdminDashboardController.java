package com.reservia.controller;

import com.reservia.repository.BookingRepository;
import com.reservia.service.AuthService;
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


	/*
	 * =========================
	 * DASHBOARD
	 * =========================
	 */
	@GetMapping("/dashboard")
	public String dashboard(Model model) {

		model.addAttribute("activePage", "dashboard");

		LocalDate today = LocalDate.now();

		// Dashboard stats
		model.addAttribute("todayBookings",
				bookingRepository.countTodayBookings(today));

		model.addAttribute("monthRevenue",
				bookingRepository.revenueByMonth(
						today.getMonthValue(),
						today.getYear()
				));

		model.addAttribute("occupiedRooms",
				bookingRepository.countOccupiedRoomsToday(today));

		model.addAttribute("totalRooms",
				bookingRepository.count());

		// Recent bookings
		// model.addAttribute("recentBookings",
		//      bookingRepository.findTop10ByOrderByBookingDateDesc());

		return "pages/adminDashboard/dashboard";
	}


	/*
	 * =========================
	 * USERS
	 * =========================
	 */
	@GetMapping("/users")
	public String users(Model model) {

		model.addAttribute("activePage", "users");

		return "pages/adminDashboard/users";
	}


	/*
	 * =========================
	 * BOOKINGS
	 * =========================
	 */
	@GetMapping("/bookings")
	public String bookings(Model model) {

		model.addAttribute("activePage", "bookings");

		return "pages/adminDashboard/bookings";
	}


	/*
	 * =========================
	 * ROOMS
	 * =========================
	 */
	@GetMapping("/rooms")
	public String rooms(Model model) {

		model.addAttribute("activePage", "rooms");

		return "pages/adminDashboard/rooms";
	}


	/*
	 * =========================
	 * SETTINGS
	 * =========================
	 */
	@GetMapping("/settings")
	public String settings(Model model) {

		model.addAttribute("activePage", "settings");

		return "pages/adminDashboard/settings";
	}

}