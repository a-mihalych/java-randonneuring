package ru.mihalych.randonneuring.bot.config;

import java.time.LocalDateTime;

public class ParametersBrevet {

    private static int countKP = 3;
    private static LocalDateTime start = LocalDateTime.of(2023, 9, 9, 5, 0);
    public static final String CSV_FILE_PATH = "./result.csv";
    public static final String IZHEVSK = "IJEVSK";
    public static final String KOD_CLUB = "511062";

    public static int getCountKP() {
        return countKP;
    }

    public static void setCountKP(int countKP) {
        ParametersBrevet.countKP = countKP;
    }

    public static LocalDateTime getStart() {
        return start;
    }

    public static void setStart(LocalDateTime start) {
        ParametersBrevet.start = start;
    }

    public static LocalDateTime correct(int minutes) {
        return start.plusMinutes(minutes);
    }
}
