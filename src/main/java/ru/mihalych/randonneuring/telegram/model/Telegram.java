package ru.mihalych.randonneuring.telegram.model;

import jakarta.persistence.*;
import lombok.*;
import ru.mihalych.randonneuring.user.model.User;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "telegrams")
public class Telegram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "telegram_id")
    private Integer id;
    @Column(name = "telegram_user_name")
    private String name;
    @Column(name = "telegram_user_chat_id")
    private Long telegramUserChatId;
    @Column(name = "telegram_admin")
    private Boolean telegramAdmin;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_bot")
    private StatusBotForUser statusBot;
}
