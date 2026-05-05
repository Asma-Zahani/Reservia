package com.reservia.controller;

import com.reservia.entity.Room;
import com.reservia.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class RoomController {
	private final RoomService roomService;

	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}

	@RequestMapping("/rooms")
	public String rooms(Model model,
						@RequestParam(defaultValue = "0") int page,
						@RequestParam(defaultValue = "6") int size) {

		Page<Room> roomPage = roomService.getRooms(page, size);

		model.addAttribute("rooms", roomPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", roomPage.getTotalPages());

		return "pages/rooms/list";
	}

	@GetMapping("/rooms/{id}")
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

	@GetMapping("/rooms/available")
	public String getAvailableRooms(@RequestParam String startDate, @RequestParam String endDate, Model model) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate start = LocalDate.parse(startDate, formatter);
		LocalDate end = LocalDate.parse(endDate, formatter);

		List<Room> rooms = roomService.getAvailableRooms(start, end);

		model.addAttribute("rooms", rooms);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "pages/booking/booking"; // ou une page dédiée
	}

}
