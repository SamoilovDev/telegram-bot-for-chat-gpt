package com.samoilov.dev.telegrambotchatgptconnector.config;

import com.samoilov.dev.telegrambotchatgptconnector.controller.ChatGptBotController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
public class TelegramConfig {

    private final ChatGptBotController chatGptBotController;

    @EventListener({ContextRefreshedEvent.class})
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(chatGptBotController);
        return api;
    }

}
