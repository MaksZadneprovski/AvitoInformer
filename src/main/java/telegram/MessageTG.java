package telegram;

import model.FlatAvito;
import model.Yaxis;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MessageTG {
    public static SendMessage sendInlineKeyBoardMessageCity(long chatId, String message){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (String s: FlatAvito.link.keySet()) {
            List<InlineKeyboardButton> list = new ArrayList<>();
            list.add(new InlineKeyboardButton().setText(s).setCallbackData(s));
            rowList.add(list);
        }
        List<InlineKeyboardButton> list = new ArrayList<>();
        list.add(new InlineKeyboardButton().setText("Выбрать все").setCallbackData("Выбрать все"));
        rowList.add(list);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText(message+", привет!\n" + " Выбери город").setReplyMarkup(inlineKeyboardMarkup);
    }
    public static SendMessage sendInlineKeyBoardMessagePeriod(long chatId){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> list = new ArrayList<>();
        list.add(new InlineKeyboardButton().setText("Неделя").setCallbackData("Неделя"));
        list.add(new InlineKeyboardButton().setText("Месяц").setCallbackData("Месяц"));
        list.add(new InlineKeyboardButton().setText("Пол года").setCallbackData("Пол года"));
        list.add(new InlineKeyboardButton().setText("Год").setCallbackData("Год"));
        rowList.add(list);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("А теперь период").setReplyMarkup(inlineKeyboardMarkup);
    }
    public static SendMessage sendInlineKeyBoardMessageYaxis(long chatId){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> list0 = new ArrayList<>();
        list0.add(new InlineKeyboardButton().setText("Средняя цена квартир").setCallbackData(String.valueOf(Yaxis.AveragePrice)));
        rowList.add(list0);
        List<InlineKeyboardButton> list1 = new ArrayList<>();
        list1.add(new InlineKeyboardButton().setText("Медианная цена квартир").setCallbackData(String.valueOf(Yaxis.MedianPrice)));
        rowList.add(list1);
        List<InlineKeyboardButton> list2 = new ArrayList<>();
        list2.add(new InlineKeyboardButton().setText("Ср-яя цена 1-к кв.").setCallbackData(String.valueOf(Yaxis.AveragePrice1)));
        list2.add(new InlineKeyboardButton().setText("Мед-ая цена 1-к кв.").setCallbackData(String.valueOf(Yaxis.MedianPrice1)));
        rowList.add(list2);
        List<InlineKeyboardButton> list3 = new ArrayList<>();
        list3.add(new InlineKeyboardButton().setText("Ср-яя цена 2-к кв.").setCallbackData(String.valueOf(Yaxis.AveragePrice2)));
        list3.add(new InlineKeyboardButton().setText("Мед-ая цена 2-к кв.").setCallbackData(String.valueOf(Yaxis.MedianPrice2)));
        rowList.add(list3);
        List<InlineKeyboardButton> list4 = new ArrayList<>();
        list4.add(new InlineKeyboardButton().setText("Ср-яя цена 3-к кв.").setCallbackData(String.valueOf(Yaxis.AveragePrice3)));
        list4.add(new InlineKeyboardButton().setText("Мед-ая цена 3-к кв.").setCallbackData(String.valueOf(Yaxis.MedianPrice3)));
        rowList.add(list4);
        List<InlineKeyboardButton> list5 = new ArrayList<>();
        list5.add(new InlineKeyboardButton().setText("Ср-яя цена за кв.м.").setCallbackData(String.valueOf(Yaxis.AveragePriceMeter)));
        list5.add(new InlineKeyboardButton().setText("Мед-ая цена за кв.м.").setCallbackData(String.valueOf(Yaxis.MedianPriceMeter)));
        rowList.add(list5);
        List<InlineKeyboardButton> list6 = new ArrayList<>();
        list6.add(new InlineKeyboardButton().setText("Кол-во объявлений").setCallbackData(String.valueOf(Yaxis.CountFlats)));
        rowList.add(list6);
        List<InlineKeyboardButton> list7 = new ArrayList<>();
        list7.add(new InlineKeyboardButton().setText("Топ 20 дешевых").setCallbackData("Топ"));
        rowList.add(list7);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("По какому параметру нужна статистика?").setReplyMarkup(inlineKeyboardMarkup);
    }

    public static SendMessage sendTop(long chatId,String text){
        return new SendMessage().setChatId(chatId).setText(text);
    }
}
