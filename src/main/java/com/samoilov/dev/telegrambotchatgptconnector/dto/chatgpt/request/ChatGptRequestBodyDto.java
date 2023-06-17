package com.samoilov.dev.telegrambotchatgptconnector.dto.chatgpt.request;

import com.samoilov.dev.telegrambotchatgptconnector.dto.chatgpt.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptRequestBodyDto {

    private String model;

    private List<MessageDto> messages;

    private int n;

    private double temperature;

}
