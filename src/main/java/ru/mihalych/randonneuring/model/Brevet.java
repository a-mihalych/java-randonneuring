package ru.mihalych.randonneuring.model;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    private Integer distance;
}
