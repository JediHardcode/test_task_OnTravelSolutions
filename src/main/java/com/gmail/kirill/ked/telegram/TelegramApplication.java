package com.gmail.kirill.ked.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication(scanBasePackages = "com.gmail.kirill.ked.telegram")
@EntityScan(basePackages = {"com.gmail.kirill.ked.telegram.repository.model"})
@PropertySource("classpath:telegram.properties")
public class TelegramApplication {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(TelegramApplication.class, args);
    }
}