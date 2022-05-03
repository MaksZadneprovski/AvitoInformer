package model;

import lombok.Data;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public class FlatAvito {
    public static Map<String, String> link = new HashMap<>();

    static {
        link.put("Волжский", "https://www.avito.ru/volgogradskaya_oblast_volzhskiy/kvartiry/prodam-ASgBAgICAUSSA8YQ?cd=1");
        link.put("Волгоград", "https://www.avito.ru/volgograd/kvartiry/prodam-ASgBAgICAUSSA8YQ?cd=1");
        link.put("Москва", "https://www.avito.ru/moskva/kvartiry/prodam-ASgBAgICAUSSA8YQ?cd=1");
    }
    private long price;
    private int priceDollar;
    private int dollar;
    private int pricePerMeter;
    private int numberOfRooms;
    private double square;
    private int floors;
    private String city;
    private String address;
    private String href;
    private long date;

}
