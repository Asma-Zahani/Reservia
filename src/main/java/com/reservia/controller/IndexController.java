package com.reservia.controller;

import com.reservia.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	private final RoomRepository roomRepository;

	public IndexController(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@GetMapping( "/")
	public String index(Model model) {
		model.addAttribute("rooms", roomRepository.findTop10ByOrderByIdDesc());
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

	@GetMapping("/404")
	public String notFound() {
		return "error/404";
	}
}
