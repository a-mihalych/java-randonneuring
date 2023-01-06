package ru.mihalych.randonneuring.model;

import lombok.*;

@Setter
@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Brevet {

    private Integer id;
    private String name;
    private Integer distance;
}
