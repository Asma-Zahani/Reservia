package com.reservia.controller;

import com.reservia.entity.Booking;
import com.reservia.service.AuthService;

import com.reservia.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AuthService authService;
    @Autowired
    private BookingService bookingService;

	@GetMapping
	public String dashboard(Model model) {
		model.addAttribute("activePage", "dashboard");
		model.addAttribute("user", authService.getCurrentUser());
		return "pages/account/dashboard";
	}

	@GetMapping("/details")
	public String account(Model model) {
		model.addAttribute("activePage", "details");
		model.addAttribute("user", authService.getCurrentUser());
		return "pages/account/details";
	}

	@PostMapping("/update")
	public String updateAccount(@RequestParam String nom, @RequestParam String email, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		boolean emailChanged = authService.isEmailChanged(email);
		boolean success = authService.updateAccount(nom, email);
		if (success) {
			if (emailChanged) {
				SecurityContextHolder.clearContext();
				request.getSession().invalidate();
				return "redirect:/";
			}
			redirectAttributes.addFlashAttribute("successMessage", "Account updated successfully.");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to update account.");
		}
		return "redirect:/account/details";
	}

	@GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute("activePage", "change-password");
        return "pages/account/change-password";
    }

    @PostMapping("/change-password")
    public String handleChangePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            return "redirect:/account/change-password";
        }

        boolean success = authService.changePassword(currentPassword, newPassword);
        
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Mot de passe mis à jour !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Ancien mot de passe incorrect.");
        }

        return "redirect:/account/change-password";
    }

	@GetMapping("/bookings")
	public String bookings(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, Model model) {
		var user = authService.getCurrentUser();

		Page<Booking> bookingPage = bookingService.getActiveBookings(user, page, size);

		model.addAttribute("activePage", "bookings");
		model.addAttribute("bookings", bookingPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", bookingPage.getTotalPages());

		return "pages/account/bookings";
	}

	@GetMapping("/history")
	public String history(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, Model model) {
		var user = authService.getCurrentUser();

		Page<Booking> bookingPage = bookingService.getHistoryBookings(user, page, size);

		model.addAttribute("activePage", "history");
		model.addAttribute("bookings", bookingPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", bookingPage.getTotalPages());

		return "pages/account/history";
	}
}
