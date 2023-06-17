package com.samoilov.dev.telegrambotchatgptconnector.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@ConfigurationProperties(prefix = "chat.gpt")
@PropertySource(value = "classpath:application.properties")
public class ChatGptProperties {

    @Value("${chat.gpt.token}")
    private String token;

    @Value("${chat.gpt.url}")
    private String url;

    @Value("${chat.gpt.model}")
    private String model;

}
