package telegram;

import model.Data;
import model.FlatAvito;
import model.Periods;
import model.Yaxis;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageTG {
    public static SendMessage sendInlineKeyBoardMessageCity(String chatId){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        Set<String> links = new HashSet<>(Data.link.keySet());
        for (String s: links) {
            List<InlineKeyboardButton> list = new ArrayList<>();
            InlineKeyboardButton ikb = new InlineKeyboardButton();
            ikb.setText(s);
            ikb.setCallbackData(s);
            list.add(ikb);
            rowList.add(list);
        }
        List<InlineKeyboardButton> list = new ArrayList<>();
        InlineKeyboardButton ikb = new InlineKeyboardButton();
        ikb.setText("Выбрать все");
        ikb.setCallbackData("All City");
        list.add(ikb);
        rowList.add(list);
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText("Выбери город");
        sm.setReplyMarkup(inlineKeyboardMarkup);
        return sm;
    }
    public static SendMessage sendInlineKeyBoardMessagePeriod(String chatId){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> list = new ArrayList<>();
        list.add(getInlineKeyboardButton("Неделя",String.valueOf(Periods.WEEK)));
        list.add(getInlineKeyboardButton("Месяц",String.valueOf(Periods.MONTH)));
        list.add(getInlineKeyboardButton("Пол года",String.valueOf(Periods.HALF_YEAR)));
        list.add(getInlineKeyboardButton("Год",String.valueOf(Periods.YEAR)));
        rowList.add(list);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return getSendMessageWithKeyboard(chatId,"Выбери период",inlineKeyboardMarkup);
    }
    public static SendMessage sendInlineKeyBoardMessageYaxis(String chatId){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> list0 = new ArrayList<>();
        list0.add(getInlineKeyboardButton("Средняя цена квартир",String.valueOf(Yaxis.AveragePrice)));
        rowList.add(list0);
        List<InlineKeyboardButton> list1 = new ArrayList<>();
        list1.add(getInlineKeyboardButton("Медианная цена квартир",String.valueOf(Yaxis.MedianPrice)));

        rowList.add(list1);
        List<InlineKeyboardButton> list2 = new ArrayList<>();
        list2.add(getInlineKeyboardButton("Ср-яя цена 1-к кв.",String.valueOf(Yaxis.AveragePrice1)));

        list2.add(getInlineKeyboardButton("Мед-ая цена 1-к кв.",String.valueOf(Yaxis.MedianPrice1)));

        rowList.add(list2);
        List<InlineKeyboardButton> list3 = new ArrayList<>();
        list3.add(getInlineKeyboardButton("Ср-яя цена 2-к кв.",String.valueOf(Yaxis.AveragePrice2)));

        list3.add(getInlineKeyboardButton("Мед-ая цена 2-к кв.",String.valueOf(Yaxis.MedianPrice2)));

        rowList.add(list3);
        List<InlineKeyboardButton> list4 = new ArrayList<>();
        list4.add(getInlineKeyboardButton("Ср-яя цена 3-к кв.",String.valueOf(Yaxis.AveragePrice3)));

        list4.add(getInlineKeyboardButton("Мед-ая цена 3-к кв.",String.valueOf(Yaxis.MedianPrice3)));

        rowList.add(list4);
        List<InlineKeyboardButton> list5 = new ArrayList<>();
        list5.add(getInlineKeyboardButton("Ср-яя цена за кв.м.",String.valueOf(Yaxis.AveragePriceMeter)));

        list5.add(getInlineKeyboardButton("Мед-ая цена за кв.м.",String.valueOf(Yaxis.MedianPriceMeter)));

        rowList.add(list5);
        List<InlineKeyboardButton> list6 = new ArrayList<>();
        list6.add(getInlineKeyboardButton("Кол-во объявлений",String.valueOf(Yaxis.CountFlats)));

        rowList.add(list6);
        List<InlineKeyboardButton> list7 = new ArrayList<>();
        list7.add(getInlineKeyboardButton("Top-20","Top"));

        rowList.add(list7);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return getSendMessageWithKeyboard(chatId,"Выбери параметр, по которому нужна статистика",inlineKeyboardMarkup);
    }


    public static InlineKeyboardButton getInlineKeyboardButton(String text,String callbackData){
        InlineKeyboardButton ikb = new InlineKeyboardButton();
        ikb.setText(text);
        ikb.setCallbackData(callbackData);
        return ikb;
    }
    public static SendMessage getKeyboardButton(String chatId){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Получить график");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return getSendMessageWithKeyboard(chatId,"ᅠ ᅠ",keyboardMarkup );
    }
    public static SendMessage getSendMessageWithKeyboard(String chatId, String text, ReplyKeyboard replyKeyboard){
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(text);
        sm.setReplyMarkup(replyKeyboard);
        return sm;
    }

    public static SendMessage sendMyMessage(String chatId, String text) {
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(text);
        return sm;
    }
    public static SendMessage sendStartMessage(User user) {
        SendMessage sm = new SendMessage();
        sm.setChatId(String.valueOf(user.getChatId()));
        sm.setText("Привет, "+user.getUsername()+"\nЭтот Бот анализирует объявления на Авито и сохраняет информацию о разных показателях рынка недвижимости, а если его попросить, то нарисует тебе график.\n" +
                "Что-бы Бот знал какой график нужно нарисовать, необходимо открыть меню и выбрать:\n" +
                "Город - может быть один, несколько или все;\n" +
                "Период - время, за которое будет собрана статистика;\n" +
                "Параметр - критерий, по которому будет собрана статистика.\n");
        return sm;
    }
}
