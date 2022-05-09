package model;

import java.util.ArrayList;
import java.util.List;

public class Constans {
    public static final int dollar = 80;
    public static List<String> periods = new ArrayList<>();
    static {
        periods.add("Год");
        periods.add("Пол года");
        periods.add("Месяц");
        periods.add("Неделя");
    }

}
