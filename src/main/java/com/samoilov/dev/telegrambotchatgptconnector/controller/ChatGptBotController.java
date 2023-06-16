package com.samoilov.dev.telegrambotchatgptconnector.controller;

import com.samoilov.dev.telegrambotchatgptconnector.config.properties.TelegramProperties;
import com.samoilov.dev.telegrambotchatgptconnector.service.ChatGptBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatGptBotController extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;

    private final ChatGptBotService chatGptBotService;

    @Override
    public String getBotUsername() {
        return telegramProperties.getName();
    }

    @Override
    public String getBotToken() {
        return telegramProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage responseMessage = chatGptBotService.generateResponseMessage(update);
        try {
            super.execute(responseMessage);
            log.info("Message has been sent: ".concat(Objects.requireNonNull(responseMessage).getText()));
        } catch (TelegramApiException e) {
            log.warn("Error caught: ".concat(e.getLocalizedMessage()));
        }
    }
}
