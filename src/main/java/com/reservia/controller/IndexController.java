package com.reservia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		model.addAttribute("rooms", "rooms");
		return "pages/rooms/list";
	}

	@RequestMapping(value = "/rooms/1", method = RequestMethod.GET)
	public String room_details(Model model) {
		model.addAttribute("room", "room");
		return "pages/rooms/details";
	}

	//return "redirect:/rooms/rooms";
}
