package model;

import telegram.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Data {
    public static final int dollar = 80;
    public static List<User> settings = new ArrayList<>();

    public static User getUserById(Long id){
        List<User> users = settings.stream().filter(x -> x.getChatId() == id).collect(Collectors.toList());
        if (!users.isEmpty()){
            return users.get(0);
        }
        return null;
    }
}
