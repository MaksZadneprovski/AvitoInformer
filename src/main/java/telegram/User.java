package telegram;

import lombok.Data;
import model.Periods;
import model.Yaxis;

import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long chatId;
    private String username;
    private Set<String> city;
    private Periods period;
    private Yaxis parameter;



    public User(Long chatId, String username) {
        this.chatId = chatId;
        this.username = username;
        this.city = new HashSet<>();
    }
}
