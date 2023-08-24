package ru.mihalych.randonneuring.user.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "en_name_i")
    private String enNameI;
    @Column(name = "en_name_f")
    private String enNameF;
    @Column(name = "save_close")
    private Boolean saveClose;
}
