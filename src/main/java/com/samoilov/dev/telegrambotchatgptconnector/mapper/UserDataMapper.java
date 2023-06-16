package com.samoilov.dev.telegrambotchatgptconnector.mapper;

import com.samoilov.dev.telegrambotchatgptconnector.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Component
public class UserDataMapper {

    public UserEntity mapTelegramUserToEntity(User user) {
        return UserEntity
                .builder()
                .telegramId(user.getId())
                .firstName(user.getFirstName())
                .lastName(Objects.isNull(user.getLastName()) ? EMPTY : user.getLastName())
                .userName(Objects.isNull(user.getUserName()) ? EMPTY : user.getUserName())
                .languageCode(Objects.isNull(user.getLanguageCode()) ? "eng" : user.getLanguageCode())
                .isPremium(Objects.isNull(user.getIsPremium()) ? Boolean.FALSE : user.getIsPremium())
                .build();
    }

    private User mapEntityToTelegramUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getTelegramId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setUserName(userEntity.getUserName());
        user.setLanguageCode(userEntity.getLanguageCode());
        user.setIsPremium(userEntity.getIsPremium());
        return user;
    }

}
