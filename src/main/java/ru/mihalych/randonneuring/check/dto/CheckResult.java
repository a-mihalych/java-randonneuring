package ru.mihalych.randonneuring.check.dto;

import lombok.*;

import java.util.Arrays;

@Setter
@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CheckResult implements Comparable {

    private String nameFirst;
    private String nameLast;
    private String result;

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        CheckResult checkResult = (CheckResult) o;
        if (checkResult.result.equals("DNF")) {
            return -1;
        }
        if (this.result.equals("DNF")) {
            return 1;
        }
        int[] time1 = Arrays.stream(this.result.split(":"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
        int[] time2 = Arrays.stream(checkResult.result.split(":"))
                            .mapToInt(Integer::parseInt)
                            .toArray();
        int minutes1 = time1[0] * 60 + time1[1];
        int minutes2 = time2[0] * 60 + time2[1];
        return minutes1 - minutes2;
    }
}
