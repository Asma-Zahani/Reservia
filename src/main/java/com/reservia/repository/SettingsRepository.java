package com.reservia.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reservia.entity.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
