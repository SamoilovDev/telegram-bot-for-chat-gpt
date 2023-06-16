package com.samoilov.dev.telegrambotchatgptconnector.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramProperties {

    private String name;

    private String token;

}
