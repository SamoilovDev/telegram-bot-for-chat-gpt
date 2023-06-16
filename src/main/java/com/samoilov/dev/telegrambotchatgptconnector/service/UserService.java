package com.samoilov.dev.telegrambotchatgptconnector.service;

import com.samoilov.dev.telegrambotchatgptconnector.mapper.UserDataMapper;
import com.samoilov.dev.telegrambotchatgptconnector.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDataMapper userDataMapper;

    private final UserRepository userRepository;

}
