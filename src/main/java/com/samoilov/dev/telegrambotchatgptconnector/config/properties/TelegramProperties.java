package com.samoilov.dev.telegrambotchatgptconnector.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@ConfigurationProperties(prefix = "telegram.bot")
@PropertySource(value = "classpath:application.properties")
public class TelegramProperties {

    @Value("${telegram.bot.name}")
    private String name;

    @Value("${telegram.bot.token}")
    private String token;

}
