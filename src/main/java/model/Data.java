package model;

import db.LiksDAO;
import telegram.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Data {
    public static final int dollar = 80;

    public static List<User> settings = new ArrayList<>();

    public static User getUserById(Long id){
        List<User> users = settings.stream().filter(x -> x.getChatId().equals(id)).collect(Collectors.toList());
        if (!users.isEmpty()){
            return users.get(0);
        }
        return null;
    }

    public   static Map<String, String> link;

    static {
        try {
            link = LiksDAO.getLinks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
