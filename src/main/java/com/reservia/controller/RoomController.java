package com.reservia.controller;

import com.reservia.entity.Room;
import com.reservia.service.BookingService;
import com.reservia.service.ExtraServiceService;
import com.reservia.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rooms")
public class RoomController {
	private final RoomService roomService;
	private final ExtraServiceService extraServiceService;
	private final BookingService bookingService;

	public RoomController(RoomService roomService, ExtraServiceService extraServiceService, BookingService bookingService) {
		this.roomService = roomService;
		this.extraServiceService = extraServiceService;
		this.bookingService = bookingService;
	}

	@GetMapping
	public String rooms(Model model,
						@RequestParam(defaultValue = "0") int page,
						@RequestParam(defaultValue = "6") int size) {

		Page<Room> roomPage = roomService.getRooms(page, size);

		model.addAttribute("rooms", roomPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", roomPage.getTotalPages());

		return "pages/rooms/list";
	}

	@GetMapping("/{id}")
	public String roomDetails(@PathVariable Long id, Model model) {

		Room room = roomService.findById(id);

		if (room == null) {
			return "redirect:/rooms";
		}

		model.addAttribute("room", room);
		List<Room> similarRooms = roomService.findSimilarRooms(room.getType(), id);
		model.addAttribute("similar_rooms", similarRooms);

		return "pages/rooms/details";
	}

	@GetMapping("/available")
	public String getAvailableRooms(@RequestParam String startDate, @RequestParam String endDate, Model model) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate start = LocalDate.parse(startDate, formatter);
		LocalDate end = LocalDate.parse(endDate, formatter);

		List<Room> rooms = roomService.getAvailableRooms(start, end);

		model.addAttribute("rooms", rooms);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("extraServices", extraServiceService.getAll());
		return "pages/rooms/booking";
	}

	@GetMapping("/{id}/availableQuantity")
	public ResponseEntity<Map<String, Object>> getAvailableQuantity(@PathVariable Long id,
			@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		Room room = roomService.findById(id);

		Integer reserved = bookingService.getReservedQuantityBetweenDates(id, startDate, endDate);
		Integer available = room.getTotalQuantity() - reserved;

		Map<String, Object> response = new HashMap<>();
		response.put("roomId", id);
		response.put("reserved", reserved);
		response.put("available", available);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}/disabledDates")
	@ResponseBody
	public List<String> getDisabledDates(@PathVariable Long id) {
		return bookingService.getDisabledDatesForRoom(id);
	}
}
