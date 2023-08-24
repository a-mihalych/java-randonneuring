package ru.mihalych.randonneuring.check.model;

import jakarta.persistence.*;
import lombok.*;
import ru.mihalych.randonneuring.user.model.User;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checks")
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_id")
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "check_time")
    private LocalDateTime checkTime;
    private Integer kp;
}
