package com.reservia.controller;

import com.reservia.entity.Reservation;
import com.reservia.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private AuthService service;

	@GetMapping
	public String dashboard(Model model) {
		model.addAttribute("activePage", "dashboard");
		model.addAttribute("client", service.getCurrentClient());
		return "pages/dashboard/dashboard";
	}

	@GetMapping("/account")
	public String account(Model model) {
		model.addAttribute("activePage", "account");
		model.addAttribute("client", service.getCurrentClient());
		return "pages/dashboard/account-details";
	}

	@PostMapping("/account/update")
public String updateAccount(@RequestParam String nom,
                            @RequestParam String email,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes) {
		boolean emailChanged = service.isEmailChanged(email);
		boolean success = service.updateAccount(nom, email);
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

        boolean success = service.changePassword(currentPassword, newPassword);
        
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Mot de passe mis à jour !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Ancien mot de passe incorrect.");
        }

        return "redirect:/dashboard/change-password"; // Redirige vers le GET
    }


	

	@GetMapping("/reservations")
	public String reservations(Model model) {
		model.addAttribute("activePage", "reservations");

		List<Reservation> reservations = new ArrayList<>();

		Reservation r1 = new Reservation();
		r1.setId(1L);
		r1.setDateDebut(LocalDate.of(2026, 5, 10));
		r1.setDateFin(LocalDate.of(2026, 5, 15));
		r1.setDateReservation(LocalDate.now());
		r1.setTotalPrix(250.0);

		Reservation r2 = new Reservation();
		r2.setId(2L);
		r2.setDateDebut(LocalDate.of(2026, 6, 1));
		r2.setDateFin(LocalDate.of(2026, 6, 5));
		r2.setDateReservation(LocalDate.now());
		r2.setTotalPrix(180.0);

		reservations.add(r1);
		reservations.add(r2);

		model.addAttribute("reservations", reservations);

		return "pages/dashboard/reservations";
	}

	@GetMapping("/history")
	public String history(Model model) {
		model.addAttribute("activePage", "history");
		return "pages/dashboard/history";
	}
}
