package com.samoilov.dev.telegrambotchatgptconnector.service;

import com.samoilov.dev.telegrambotchatgptconnector.config.properties.ChatGptProperties;
import com.samoilov.dev.telegrambotchatgptconnector.dto.chatgpt.request.ChatGptRequestBodyDto;
import com.samoilov.dev.telegrambotchatgptconnector.dto.chatgpt.MessageDto;
import com.samoilov.dev.telegrambotchatgptconnector.dto.chatgpt.response.ChatGptResponseBodyDto;
import com.samoilov.dev.telegrambotchatgptconnector.dto.chatgpt.response.ChoiceDto;
import com.samoilov.dev.telegrambotchatgptconnector.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Objects;

import static com.samoilov.dev.telegrambotchatgptconnector.service.util.MessageText.ERROR_RESPONSE_MESSAGE;
import static com.samoilov.dev.telegrambotchatgptconnector.service.util.MessageText.INFORMATION_MESSAGE;
import static com.samoilov.dev.telegrambotchatgptconnector.service.util.MessageText.START_MESSAGE;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGptBotService {

    private final WebClient webClient;

    private final UserService userService;

    private final ChatGptProperties chatGptProperties;


    public SendMessage generateResponseMessage(Update update) {
        String message = this.getMessage(update);
        BotApiObject botApiObject = Objects.isNull(update.getMessage())
                        ? update.getCallbackQuery()
                        : update.getMessage();

        userService.incrementCount(((Message) botApiObject).getFrom());

        return switch (MessageType.parse(message.split("\\s+")[0])) {
            case START -> this.getStartMessage(((Message) botApiObject).getChatId());
            case HELP -> this.getHelpMessage(((Message) botApiObject).getChatId());
            case MESSAGE -> this.getChatGPTResponse(botApiObject, message);
        };
    }

    private SendMessage getStartMessage(Long chatId) {
        log.info("Sending start message to user with chatId: {}", chatId);
        return SendMessage
                .builder()
                .chatId(chatId)
                .text(START_MESSAGE)
                .build();
    }

    private SendMessage getHelpMessage(Long chatId) {
        log.info("Sending help message to user with chatId: {}", chatId);
        return SendMessage
                .builder()
                .chatId(chatId)
                .text(INFORMATION_MESSAGE)
                .build();
    }

    private SendMessage getChatGPTResponse(BotApiObject botApiObject, String prompt) {
        Message message = (Message) botApiObject;

        return webClient
                .post()
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setBearerAuth(chatGptProperties.getToken());
                })
                .bodyValue(
                        ChatGptRequestBodyDto
                                .builder()
                                .model(chatGptProperties.getModel())
                                .messages(List.of(MessageDto.builder().content(prompt).build()))
                                .build()
                )
                .retrieve()
                .bodyToMono(ChatGptResponseBodyDto.class)
                .map(fullResponse -> {
                    ChoiceDto firstChoice = fullResponse.getChoices().get(0);
                    return firstChoice.getFinishReason().equals("stop")
                            ? firstChoice.getMessage().getContent()
                            : ERROR_RESPONSE_MESSAGE.formatted(firstChoice.getFinishReason());
                })
                .map(responseText -> SendMessage.builder().chatId(message.getChatId()).text(responseText).build())
                .block();
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
