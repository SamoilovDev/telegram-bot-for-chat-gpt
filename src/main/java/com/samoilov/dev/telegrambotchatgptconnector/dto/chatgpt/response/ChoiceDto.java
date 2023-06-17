package com.samoilov.dev.telegrambotchatgptconnector.dto.chatgpt.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samoilov.dev.telegrambotchatgptconnector.dto.chatgpt.MessageDto;
import lombok.Data;

@Data
public class ChoiceDto {

    private Long index;

    private MessageDto message;

    @JsonProperty("finish_reason")
    private String finishReason;

}
