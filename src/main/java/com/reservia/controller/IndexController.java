package com.reservia.controller;

import com.reservia.entities.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/reservia")
public class IndexController {
	@GetMapping( "/")
	public String home() {
		 return "pages/home";
	 }

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact() {
		return "pages/contact";
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about() {
		return "pages/about";
	}

	@RequestMapping(value = "/service", method = RequestMethod.GET)
	public String service() {
		return "pages/service";
	}

	@RequestMapping(value = "/rooms", method = RequestMethod.GET)
	public String rooms(Model model) {
		List<Room> rooms = List.of(
				new Room(1L,"/images/pages/room/1.webp", "Deluxe", 20, 1, 220, "Room Classic Whether you have questions, need assistance, or simply want to share. 220DT..."),
				new Room(2L,"/images/pages/room/2.webp", "Single", 30, 2, 120, "Room Classic Whether you have questions, need assistance, or simply want to share. 120DT..."),
				new Room(3L,"/images/pages/room/3.webp", "Triple", 50, 4, 160, "Room Classic Whether you have questions, need assistance, or simply want to share. 160DT..."),
				new Room(4L,"/images/pages/room/4.webp", "Connecting", 50, 4, 180, "Room Classic Whether you have questions, need assistance, or simply want to share. 180DT..."),
				new Room(5L,"/images/pages/room/5.webp", "Accessible", 50, 4, 200, "Room Classic Whether you have questions, need assistance, or simply want to share. 200DT..."),
				new Room(6L,"/images/pages/room/6.webp", "Double", 50, 4, 140, "Room Classic Whether you have questions, need assistance, or simply want to share. 140DT...")
		);

		model.addAttribute("rooms", rooms);
		return "pages/rooms/list";
	}

	@GetMapping("/rooms/{id}")
	public String roomDetails(@PathVariable Long id, Model model) {
		//Room room = roomService.findById(id);
		model.addAttribute("room", "room");
		return "pages/rooms/details";
	}
}
