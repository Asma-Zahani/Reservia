package com.reservia.controller;

import com.reservia.entity.Role;
import com.reservia.entity.User;
import com.reservia.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String register(AuthRequest request) {
        service.register(request);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(AuthRequest request, HttpSession session) {
        service.login(request);

        User user = (User) userService.loadUserByUsername(request.getEmail());
        session.setAttribute("user", user);

        session.setAttribute(
                "SPRING_SECURITY_CONTEXT",
                SecurityContextHolder.getContext()
        );

        if (user.getRole() == Role.ROLE_ADMIN) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/dashboard";
        }
    }
}
