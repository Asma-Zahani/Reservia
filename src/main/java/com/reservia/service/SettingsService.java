package com.reservia.service;

import org.springframework.stereotype.Service;

import com.reservia.entity.Settings;
import com.reservia.repository.SettingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final SettingsRepository settingsRepository;

    public Settings getSettings() {

        return settingsRepository.findAll()
                .stream()
                .findFirst()
                .orElse(new Settings());
    }

    public void save(Settings settings) {

        settingsRepository.save(settings);
    }
}
