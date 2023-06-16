package com.samoilov.dev.telegrambotchatgptconnector.service;

import com.samoilov.dev.telegrambotchatgptconnector.enums.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Service
@RequiredArgsConstructor
public class ChatGptBotService {

    public SendMessage generateResponseMessage(Update update) {
        String message = this.getMessage(update);
        switch (MessageType.parse(message.split("\\s+")[0])) {
            case START -> ;
            case HELP -> ;
            case MESSAGE -> ;
        }
    }

    private SendMessage getStartMessage() {

    }

    private String getMessage(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().hasText()
                    ? update.getMessage().getText()
                    : EMPTY;
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData();
        } else return EMPTY;
    }

}
