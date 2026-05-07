package com.reservia.controller;

import com.reservia.dto.BookingRequest;
import com.reservia.entity.Booking;
import com.reservia.entity.Room;
import com.reservia.service.AuthService;

import com.reservia.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private AuthService authService;
    @Autowired
    private BookingService bookingService;

	@GetMapping
	public String dashboard(Model model) {
		model.addAttribute("activePage", "dashboard");
		model.addAttribute("user", authService.getCurrentUser());
		return "pages/dashboard/dashboard";
	}

	@GetMapping("/account")
	public String account(Model model) {
		model.addAttribute("activePage", "account");
		return "pages/dashboard/account-details";
	}

	@PostMapping("/account/update")
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
		return "redirect:/dashboard/account";
	}

	@GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute("activePage", "change-password");
        return "pages/dashboard/change-password";
    }

    @PostMapping("/change-password")
    public String handleChangePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            return "redirect:/dashboard/change-password";
        }

        boolean success = authService.changePassword(currentPassword, newPassword);
        
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Mot de passe mis à jour !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Ancien mot de passe incorrect.");
        }

        return "redirect:/dashboard/change-password";
    }

	@GetMapping("/bookings")
	public String bookings(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, Model model) {
		var user = authService.getCurrentUser();

		Page<Booking> bookingPage = bookingService.getActiveBookings(user, page, size);

		model.addAttribute("activePage", "bookings");
		model.addAttribute("bookings", bookingPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", bookingPage.getTotalPages());

		return "pages/dashboard/bookings";
	}

	@GetMapping("/history")
	public String history(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, Model model) {
		var user = authService.getCurrentUser();

		Page<Booking> bookingPage = bookingService.getHistoryBookings(user, page, size);

		model.addAttribute("activePage", "history");
		model.addAttribute("bookings", bookingPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", bookingPage.getTotalPages());

		return "pages/dashboard/history";
	}
}
