package com.samoilov.dev.telegrambotchatgptconnector.repository;

import com.samoilov.dev.telegrambotchatgptconnector.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
