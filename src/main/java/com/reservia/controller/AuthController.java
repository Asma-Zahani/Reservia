package com.reservia.controller;

import com.reservia.entity.Role;
import com.reservia.entity.User;
import com.reservia.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reservia.dto.AuthRequest;
import com.reservia.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private UserService userService;

@PostMapping("/register")
public String register(AuthRequest request,
                       RedirectAttributes redirectAttributes) {

    try {

        service.register(request);

        redirectAttributes.addFlashAttribute(
                "registerSuccess",
                "Compte créé avec succès !"
        );

        return "redirect:/";

    } catch (Exception e) {

        redirectAttributes.addFlashAttribute(
                "registerError",
                e.getMessage()
        );

        return "redirect:/";
    }
}

@PostMapping("/login")
public String login(AuthRequest request,
                    HttpSession session,
                    RedirectAttributes redirectAttributes) {

    try {

        service.login(request);

        User user = (User) userService.loadUserByUsername(request.getEmail());

        session.setAttribute(
                "SPRING_SECURITY_CONTEXT",
                SecurityContextHolder.getContext()
        );


        if (user.getRole() == Role.ROLE_ADMIN) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/dashboard";

    } catch (Exception e) {

        redirectAttributes.addFlashAttribute(
                "loginError",
                "Email ou mot de passe incorrect"
        );

        return "redirect:/";
    }
}
}
