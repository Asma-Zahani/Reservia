package com.reservia.controller;

import com.reservia.dto.BookingRequest;
import com.reservia.entity.Booking;
import com.reservia.service.AuthService;

import com.reservia.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
		model.addAttribute("user", authService.getCurrentUser());
		return "pages/dashboard/account-details";
	}

	@PostMapping("/account/update")
	public String updateAccount(@RequestParam String nom,
                            @RequestParam String email,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes) {
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

    // 2. Traite le formulaire (POST)
    @PostMapping("/change-password")
    public String handleChangePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            return "redirect:/dashboard/change-password"; // Redirige vers le GET
        }

        boolean success = authService.changePassword(currentPassword, newPassword);
        
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Mot de passe mis à jour !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Ancien mot de passe incorrect.");
        }

        return "redirect:/dashboard/change-password"; // Redirige vers le GET
    }


	

	@GetMapping("/bookings")
	public String bookings(Model model) {
		var user = authService.getCurrentUser();
		List<Booking> bookings = bookingService.getBookingsByUser(user);

		model.addAttribute("activePage", "bookings");
		model.addAttribute("bookings", bookings);

		return "pages/dashboard/bookings";
	}

	@GetMapping("/bookings/edit/{id}")
	public String editBooking(@PathVariable Long id, Model model) {
		Booking booking = bookingService.getBookingById(id);
		model.addAttribute("booking", booking);
		return "";
	}

	@PostMapping("/bookings/edit/{id}")
	public String updateBooking(@PathVariable Long id, @ModelAttribute BookingRequest bookingRequest, @RequestParam Map<String,String> allParams) {
		bookingService.updateBooking(id, bookingRequest, allParams);
		return "redirect:/dashboard/bookings";
	}

	@PostMapping("/bookings/delete/{id}")
	public String deleteBooking(@PathVariable Long id) {
		bookingService.deleteBooking(id);
		return "redirect:/dashboard/bookings";
	}

	@GetMapping("/history")
	public String history(Model model) {
		var user = authService.getCurrentUser();
		List<Booking> bookings = bookingService.getBookingsByUser(user);

		model.addAttribute("activePage", "history");
		model.addAttribute("bookings", bookings);

		return "pages/dashboard/history";
	}
}
