package telegram;

import charts.TimeSeriesChart;
import db.FlatDAO;
import model.Constans;
import model.FlatAvito;
import model.Yaxis;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MessageReciever implements Runnable {
    private BotTG bot;
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;
    HashSet<String> cities = new HashSet<>();


    public MessageReciever(BotTG bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        while (true) {
            for (Object object = bot.receiveQueue.poll(); object != null; object = bot.receiveQueue.poll()) {
                analyze(object);
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void analyze(Object object) {
        if (object instanceof Update) {
            Update update = (Update) object;
            analyzeForUpdateType(update);
        }
    }

    private void analyzeForUpdateType(Update update) {
        String inputText = "";
        Message message = null;
        if (update.hasMessage()) {
            message = update.getMessage();
            inputText = message.getText();
        }else if(update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
            inputText = update.getCallbackQuery().getData();
        }
        Long chatId = message.getChatId();
        String username = message.getFrom().getFirstName()+" "+message.getFrom().getLastName();
        String finalInputText = inputText;

        if(inputText.equals("/start")){
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessageCity(chatId,username));
        } else if (FlatAvito.link.keySet().contains(inputText) || inputText.equals("Выбрать все")){
            cities.clear();
            cities.add(inputText);
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessagePeriod(chatId));
        }else if (Constans.periods.contains(inputText)){
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessageYaxis(chatId));
        }else if(Arrays.stream(Yaxis.values()).anyMatch(x->x.toString().equals(finalInputText))) {
            try {
                SendPhoto photoMessage = new SendPhoto().setNewPhoto(TimeSeriesChart.getJpeg(cities, Yaxis.valueOf(finalInputText))).setChatId(chatId);
                bot.sendQueue.add(photoMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(inputText.equals("Топ")){
            FlatDAO flatDAO = new FlatDAO();
            try {
                List<String> listTop = flatDAO.getTopList(cities);
                for (String s : listTop) {
                    bot.sendQueue.add(MessageTG.sendTop(chatId,s));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
