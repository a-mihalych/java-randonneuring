package ru.mihalych.randonneuring.message.model;

import jakarta.persistence.*;
import lombok.*;
import ru.mihalych.randonneuring.telegram.model.Telegram;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "telegram_id")
    private Telegram telegram;
    @Column(name = "message_time")
    private LocalDateTime messageTime;
    @Column(name = "message_text")
    private String text;
    private String callback;
    @Column(name = "photo_file_id")
    private String photoFileId;
}
