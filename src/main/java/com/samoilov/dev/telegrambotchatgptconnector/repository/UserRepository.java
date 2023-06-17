package com.samoilov.dev.telegrambotchatgptconnector.repository;

import com.samoilov.dev.telegrambotchatgptconnector.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByTelegramId(Long telegramId);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.activeType = 'DISABLED' WHERE u.telegramId = :telegramId")
    Optional<UserEntity> deleteByTelegramId(@Param("telegramId") Long telegramId);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.commandCounter = u.commandCounter + 1 WHERE u = :userEntity")
    void incrementCount(UserEntity userEntity);
}
