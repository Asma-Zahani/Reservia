package com.reservia;

import com.reservia.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final SettingsService settingsService;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("settings", settingsService.getSettings());
    }
}