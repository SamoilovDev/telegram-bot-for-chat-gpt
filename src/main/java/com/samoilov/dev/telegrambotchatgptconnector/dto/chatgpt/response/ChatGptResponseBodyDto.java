package com.samoilov.dev.telegrambotchatgptconnector.dto.chatgpt.response;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ChatGptResponseBodyDto {

    private String id;

    private String object;

    private Instant created;

    private List<ChoiceDto> choices;

    private UsageDto usage;

}
