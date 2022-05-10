package model;

import db.LiksDAO;
import lombok.Data;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public class FlatAvito {

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
