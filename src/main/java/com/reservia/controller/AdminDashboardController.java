package com.reservia.controller;

import com.reservia.entity.Booking;
import com.reservia.entity.BookingStatus;
import com.reservia.entity.Room;
import com.reservia.entity.User;
import com.reservia.repository.BookingRepository;
import com.reservia.service.AuthService;
import com.reservia.service.BookingService;
import com.reservia.service.RoomService;
import com.reservia.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
	@Autowired
	private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;

	@Autowired
	private BookingService bookingService;
    @Autowired
    private RoomService roomService;

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

	@GetMapping("/users")
	public String users(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, Model model) {
		Page<User> usersPage = userService.getUsers(page, size);

		model.addAttribute("users", usersPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", usersPage.getTotalPages());
		model.addAttribute("activePage", "users");

		return "pages/adminDashboard/users";
	}

	@PostMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "redirect:/admin/users";
	}

	@GetMapping("/bookings")
	public String bookings(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
		Page<Booking> bookingPage = bookingService.getAllBookings(page, size);

		model.addAttribute("bookings", bookingPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", bookingPage.getTotalPages());
		model.addAttribute("activePage", "bookings");

		return "pages/adminDashboard/bookings";
	}

	@PostMapping("/bookings/update-status/{id}")
	public String updateBookingStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
		bookingService.updateBookingStatus(id, status);
		return "redirect:/admin/bookings";
	}

	@GetMapping("/rooms")
	public String rooms(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
		Page<Room> roomPage = roomService.getRooms(page, size);

		model.addAttribute("rooms", roomPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", roomPage.getTotalPages());
		model.addAttribute("activePage", "rooms");

		return "pages/adminDashboard/rooms";
	}

	@PostMapping("/rooms/delete/{id}")
	public String deleteRoom(@PathVariable Long id) {
		roomService.deleteRoom(id);
		return "redirect:/admin/rooms";
	}

	@GetMapping("/settings")
	public String settings(Model model) {

		model.addAttribute("activePage", "settings");

		return "pages/adminDashboard/settings";
	}

}