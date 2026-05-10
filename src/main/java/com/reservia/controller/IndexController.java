package com.reservia.controller;

import com.reservia.repository.RoomRepository;
import com.reservia.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {
	private final RoomRepository roomRepository;
	private final EmailService emailService;

	public IndexController(RoomRepository roomRepository, EmailService emailService) {
		this.roomRepository = roomRepository;
		this.emailService = emailService;
	}

	@GetMapping("/")
	public String index(Model model,
						@ModelAttribute("loginError") String loginError,
						@ModelAttribute("registerError") String registerError,
						@ModelAttribute("registerSuccess") String registerSuccess,
						@ModelAttribute("success") String success) {

		model.addAttribute("rooms", roomRepository.findTop10ByOrderByIdDesc());
		return "pages/home";
	}

	@GetMapping("/contact")
	public String contact() {
		return "pages/contact";
	}

	@PostMapping("/contact")
	public String submitContact(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("msg") String message,
			RedirectAttributes redirectAttributes
	) {
		try {
			emailService.sendContactEmail(name, email, message);
			redirectAttributes.addFlashAttribute("contactSuccess", "Your message has been sent successfully!");
		} catch (MessagingException e) {
			redirectAttributes.addFlashAttribute("contactError", "Failed to send message. Please try again later.");
			e.printStackTrace();
		}

		return "redirect:/contact";
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
