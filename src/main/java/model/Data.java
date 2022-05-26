package model;

import db.LiksDAO;
import telegram.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Data {
    public static final int dollar = 80;

    public static List<User> settings = new ArrayList<>();
    private static TreeMap<String, String> links;

    public static User getUserById(Long id){
        List<User> users = settings.stream().filter(x -> x.getChatId().equals(id)).collect(Collectors.toList());
        if (!users.isEmpty()){
            return users.get(0);
        }
        return null;
    }

    public   static Map<String, String> getLinks(){
        return links;
    };
    public static void updateLinks(){
        Runnable r = () -> {
            while (true){
                try {
                    links = LiksDAO.getLinks();
                    Thread.sleep(10000);
                } catch (SQLException e) {
                    e.printStackTrace();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r).start();
    }
}
