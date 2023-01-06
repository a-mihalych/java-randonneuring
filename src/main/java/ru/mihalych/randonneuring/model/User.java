package ru.mihalych.randonneuring.model;

import lombok.*;

@Setter
@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String ruNameI;
    private String ruNameF;
    private String enNameI;
    private String enNameF;
    private String nick;
}
