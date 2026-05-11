package com.reservia.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    public EnvConfig() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("STRIPE_SECRET_KEY", dotenv.get("STRIPE_SECRET_KEY"));
        System.setProperty("STRIPE_PUBLIC_KEY", dotenv.get("STRIPE_PUBLIC_KEY"));
    }
}