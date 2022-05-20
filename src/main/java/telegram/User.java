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

    public  String parseParameter(){
        String s = "";
        if (this.getParameter()!=null) {
            switch (this.getParameter()){
                case AveragePrice3:
                    s = "Средняя цена 3-к. квартиры";
                    break;
                case AveragePrice2:
                    s = "Средняя цена 2-к. квартиры";
                    break;
                case AveragePrice1:
                    s = "Средняя цена 1-к. квартиры";
                    break;
                case AveragePrice:
                    s = "Средняя цена квартиры";
                    break;
                case MedianPrice:
                    s = "Медианная цена квартиры";
                    break;
                case MedianPrice1:
                    s = "Медианная цена 1-к. квартиры";
                    break;
                case MedianPrice2:
                    s = "Медианная цена 2-к. квартиры";
                    break;
                case MedianPrice3:
                    s = "Медианная цена 3-к. квартиры";
                    break;
                case CountFlats:
                    s = "Количество квартир в продаже";
                    break;
                case MedianPriceMeter:
                    s = "Медианная цена за м²";
                    break;
                case AveragePriceMeter:
                    s = "Средняя цена за м²";
                    break;
            }
        }
        return s;
    }
    public  String parsePeriod() {
        String s = "";
        if (this.getParameter() != null) {
            switch (this.getPeriod()) {
                case WEEK:
                    s = "Неделя";
                    break;
                case MONTH:
                    s = "Месяц";
                    break;
                case HALF_YEAR:
                    s = "Пол года";
                    break;
                case YEAR:
                    s = "Год";
                    break;
            }
        }
        return s;
    }
}
