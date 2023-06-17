package com.samoilov.dev.telegrambotchatgptconnector.service;

import com.samoilov.dev.telegrambotchatgptconnector.entity.UserEntity;
import com.samoilov.dev.telegrambotchatgptconnector.mapper.UserDataMapper;
import com.samoilov.dev.telegrambotchatgptconnector.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDataMapper userDataMapper;

    private final UserRepository userRepository;

    public UserEntity findOrSave(User user) {
        return userRepository
                .findByTelegramId(user.getId())
                .orElseGet(() -> userRepository.save(userDataMapper.mapTelegramUserToEntity(user)));
    }

    public void incrementCount(User user) {
        userRepository.incrementCount(this.findOrSave(user));
    }

    public Optional<UserEntity> deleteByTelegramId(Long telegramId) {
        return userRepository.deleteByTelegramId(telegramId);
    }

}
