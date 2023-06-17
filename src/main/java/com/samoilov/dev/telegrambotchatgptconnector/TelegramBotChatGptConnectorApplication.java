package com.samoilov.dev.telegrambotchatgptconnector;

import com.samoilov.dev.telegrambotchatgptconnector.config.properties.ChatGptProperties;
import com.samoilov.dev.telegrambotchatgptconnector.config.properties.TelegramProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({TelegramProperties.class, ChatGptProperties.class})
public class TelegramBotChatGptConnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotChatGptConnectorApplication.class, args);
    }

}
