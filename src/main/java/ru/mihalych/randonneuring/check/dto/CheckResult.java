package ru.mihalych.randonneuring.check.dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CheckResult {

    private String nameFirst;
    private String nameLast;
    private String result;
}
