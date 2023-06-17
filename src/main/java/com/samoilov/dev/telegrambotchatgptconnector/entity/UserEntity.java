package com.samoilov.dev.telegrambotchatgptconnector.entity;


import com.samoilov.dev.telegrambotchatgptconnector.enums.ActiveType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@SQLDelete(sql = "UPDATE user_table SET active_type = 'DISABLED' WHERE telegram_id = ?")
@Table(name = "user_table", uniqueConstraints = {
        @UniqueConstraint(columnNames = "telegram_id", name = "user_table_unique_telegram_id_idx")
})
public class UserEntity extends BasicEntity {

    @Column(name = "telegram_id", length = 50)
    private Long telegramId;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "language_code", length = 50)
    private String languageCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "active_type", length = 50)
    private ActiveType activeType = ActiveType.ENABLED;

    @Column(name = "is_premium")
    private Boolean isPremium;

    @Builder.Default
    private Long commandCounter = 0L;

}
