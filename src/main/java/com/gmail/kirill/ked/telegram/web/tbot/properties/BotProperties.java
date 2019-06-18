package com.gmail.kirill.ked.telegram.web.tbot.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotProperties {
    @Value("${bot.username: }")
    private String botName;
    @Value("${bot.token: }")
    private String botToken;

    public String getBotName() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }
}