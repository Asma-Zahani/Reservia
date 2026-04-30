package com.reservia.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservia.dto.AuthRequest;
import com.reservia.dto.AuthResponse;
import com.reservia.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public String register(AuthRequest request, HttpSession session) {
        AuthResponse res = service.register(request);

        session.setAttribute("token", res.getToken());
        session.setAttribute("user", request.getNom());
        return "redirect:/reservia/";
    }

    @PostMapping("/login")
    public String login(AuthRequest request, HttpSession session) {
        AuthResponse res = service.login(request);

        session.setAttribute("token", res.getToken());
        session.setAttribute("user", request.getNom());

        return "redirect:/reservia/";
    }
}
