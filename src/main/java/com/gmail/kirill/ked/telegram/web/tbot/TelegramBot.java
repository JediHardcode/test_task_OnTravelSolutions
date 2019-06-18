package com.gmail.kirill.ked.telegram.web.tbot;

import com.gmail.kirill.ked.telegram.service.StringService;
import com.gmail.kirill.ked.telegram.web.tbot.command.Command;
import com.gmail.kirill.ked.telegram.web.tbot.command.CommandAssembler;
import com.gmail.kirill.ked.telegram.web.tbot.properties.BotProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

import static com.gmail.kirill.ked.telegram.web.tbot.constant.MessagesConstant.COMMAND_NOT_EXIST;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final static Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private final CommandAssembler commandAssembler;
    private final BotProperties botProperties;
    private final StringService stringService;

    public TelegramBot(CommandAssembler commandAssembler,
                       BotProperties botProperties, StringService stringService) {
        this.commandAssembler = commandAssembler;
        this.botProperties = botProperties;
        this.stringService = stringService;
    }

    @PostConstruct
    public void init() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot(commandAssembler, botProperties, stringService));
            logger.info("bot successfully register");
        } catch (TelegramApiException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String response = handleRequestMessage(messageText);
            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(response);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getBotName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getBotToken();
    }

    private String handleRequestMessage(String messageText) {
        if (messageText.startsWith("/")) {
            Command command = commandAssembler.getCommand(messageText);
            if (command != null) {
                return command.execute();
            } else {
                logger.info("there is no command {}", messageText);
                return COMMAND_NOT_EXIST;
            }
        }
        return stringService.getInfoAboutCity(messageText);
    }
}