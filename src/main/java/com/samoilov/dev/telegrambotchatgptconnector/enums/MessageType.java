package com.samoilov.dev.telegrambotchatgptconnector.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {

    START("/start"),

    HELP("/help"),

    MESSAGE("-");

    private final String description;

    public static MessageType parse(String stringCommand) {
        String preparedStringCommand = stringCommand.toLowerCase();

        for (MessageType message: MessageType.values()) {
            if (message.getDescription().equals(preparedStringCommand)) {
                return message;
            }
        }

        return MESSAGE;
    }

}
