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
public class User {

    private Integer id;
    @NotBlank
    private String ruNameI;
    @NotBlank
    private String ruNameF;
    @NotBlank
    private String enNameI;
    @NotBlank
    private String enNameF;
    private String nick;
}
