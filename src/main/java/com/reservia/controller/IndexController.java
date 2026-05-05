package com.reservia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	@GetMapping( "/")
	public String index() {
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
