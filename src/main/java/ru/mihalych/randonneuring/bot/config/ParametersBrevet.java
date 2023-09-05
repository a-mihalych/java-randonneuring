package ru.mihalych.randonneuring.bot.config;

import java.time.LocalDateTime;
import java.util.List;

public class ParametersBrevet {

    private static int countKP = 3;
    private static List<String> namesKP = List.of("(58 км) перекресток Воткинск/Чайковский",
                                                  "(109 км) с.Шаркан,",
                                                  "(162 км) с.Якшур-Бодья",
                                                  "(200 км) парк им.Кирова");
    private static String legend = "СТАРТ: Супермаркет «Лента» на Камбарской, 6:00\n" +
            "КП-1 перекресток Воткинск/Чайковский. Отметка у дорожного указателя, 58 км, закрытие: 9:54\n" +
            "КП-2 с.Шаркан. Отметка в центре поселка, у памятника БТР-у, 109(51) км, закрытие 13:16\n" +
            "*** В центре Шаркана (80 м. прямо от перекрестка по ул. Ленина «Столовая №2», " +
            "направо от перекрестка 100 м. по ул. Советской столовая «Центральная» и кофейня Ulon). " +
            "Большое количество любых магазинов.\n" +
            "!!! От Шаркана до Бодьии длинный перегон, с очень тяжелым рельефом и минимумом магазинов. " +
            "Запасайтесь всем необходимым, и обедайте, кому нужно, в Шаркане\n" +
            "КП-3 ЯкшурБодья. Отметка у памятника Почтовой карете. 162(53) км, закрытие 16:48\n" +
            "ФИНИШ: Парк Кирова, 200(38) км, закрытие 19:30.";
//    private static LocalDateTime start = LocalDateTime.of(2023, 9, 9, 6, 0);
    private static LocalDateTime start = LocalDateTime.of(2023, 9, 5, 6, 0);
    public static final String CSV_FILE_PATH = "./result.csv";
    public static final String IZHEVSK = "IJEVSK";
    public static final String KOD_CLUB = "511062";
    public static final String[] TABLE_HEADERS = {"NOM", "PRENOM", "CLUB DU PARTICIPANT", "CODE ACP", "TEMPS"};

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
        start = start.plusMinutes(minutes);
        return start;
    }

    public static String getTxtNameKP(int kp) {
        return namesKP.get(kp - 1);
    }

    public static String getLegend() {
        return legend;
    }
}
