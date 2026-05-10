package com.reservia.controller;

import com.reservia.entity.Booking;
import com.reservia.entity.BookingStatus;
import com.reservia.entity.Room;
import com.reservia.entity.Settings;
import com.reservia.entity.User;
import com.reservia.repository.BookingRepository;
import com.reservia.service.AuthService;
import com.reservia.service.BookingService;
import com.reservia.service.RoomService;
import com.reservia.service.SettingsService;
import com.reservia.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

	@Autowired
	private AuthService authService;

	@Autowired
	private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private RoomService roomService;

	/*
	 * =========================
	 * DASHBOARD
	 * =========================
	 */
	@GetMapping("/dashboard")
	public String dashboard(Model model) {

		model.addAttribute("activePage", "dashboard");

		LocalDate today = LocalDate.now();

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
		 model.addAttribute("recentBookings", bookingRepository.findTop5ByOrderByBookingDateDesc());

		return "pages/adminDashboard/dashboard";
	}


	/*
	 * =========================
	 * USERS
	 * =========================
	 */
	@GetMapping("/users")
	public String users(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
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

	@PostMapping("/users/update/{id}")
	public String updateUser(@PathVariable Long id, @RequestParam String name, @RequestParam String email) {
		userService.getUserById(id).ifPresent(user -> {
			user.setName(name);
			user.setEmail(email);
			userService.saveUser(user);
		});

		return "redirect:/admin/users";
	}

	/*
	 * =========================
	 * BOOKINGS
	 * =========================
	 */
		@GetMapping("/bookings")
		public String bookings(
				@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "5") int size,
				Model model) {

			Page<Booking> bookingPage = bookingService.getAllBookings(page, size);

			model.addAttribute("bookings", bookingPage.getContent());
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", bookingPage.getTotalPages());
			model.addAttribute("activePage", "bookings");

			return "pages/adminDashboard/bookings";
		}

		@PostMapping("/bookings/update-status/{id}")
			public String updateBookingStatus(
					@PathVariable Long id,
					@RequestParam BookingStatus status) {

				bookingService.updateBookingStatus(id, status);

				return "redirect:/admin/bookings";
			}


	/*
	 * =========================
	 * ROOMS
	 * =========================
	 */
	@GetMapping("/rooms")
	public String rooms(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
		Page<Room> roomPage = roomService.getRooms(page, size);

		model.addAttribute("rooms", roomPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", roomPage.getTotalPages());
		model.addAttribute("activePage", "rooms");

		return "pages/adminDashboard/rooms";
	}

	@PostMapping("/rooms/add")
	public String addRoom( @RequestParam("image") MultipartFile image, @RequestParam String roomNumber, @RequestParam String type, @RequestParam int size, @RequestParam int capacity, @RequestParam double price, @RequestParam String description) throws IOException{
		Room room = new Room();
		String uploadDir = "src/main/resources/static/images/pages/room/";
		String fileName = image.getOriginalFilename();
		Path path = Paths.get(uploadDir + fileName);
		Files.copy(
				image.getInputStream(),
				path,
				StandardCopyOption.REPLACE_EXISTING
		);
		room.setImage_path("/images/pages/room/" + fileName);
		room.setRoomNumber(roomNumber);
		room.setType(type);
		room.setSize(size);
		room.setCapacity(capacity);
		room.setPrice(price);
		room.setDescription(description);
		roomService.saveRoom(room);
    return "redirect:/admin/rooms";
}

	@PostMapping("/rooms/delete/{id}")
	public String deleteRoom(@PathVariable Long id) {
		roomService.deleteRoom(id);
		return "redirect:/admin/rooms";
	}

	@PostMapping("/rooms/update/{id}")
	public String updateRoom(@PathVariable Long id,
							@RequestParam(value = "image", required = false) MultipartFile image,
							@RequestParam String roomNumber,
							@RequestParam String type,
							@RequestParam int size,
							@RequestParam int capacity,
							@RequestParam double price,
							@RequestParam String description) throws IOException {

		Room room = roomService.getRoomById(id); 
		if (image != null && !image.isEmpty()) {
			String uploadDir = "src/main/resources/static/images/room/";
			String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
			Path path = Paths.get(uploadDir + fileName);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			room.setImage_path("/images/room/" + fileName);
		}
		room.setRoomNumber(roomNumber);
		room.setType(type);
		room.setSize(size);
		room.setCapacity(capacity);
		room.setPrice(price);
		room.setDescription(description);
		roomService.saveRoom(room);

		return "redirect:/admin/rooms";
	}

	/*
	 * =========================
	 * SETTINGS
	 * =========================
	 */
	@GetMapping("/settings")
	public String settings(Model model) {

		model.addAttribute("settings",
				settingsService.getSettings());

		model.addAttribute("activePage", "settings");

		return "pages/adminDashboard/settings";
	}

	@PostMapping("/settings")
	public String saveSettings(@ModelAttribute Settings settings) {

		settingsService.save(settings);

		return "redirect:/admin/settings";
	}

}