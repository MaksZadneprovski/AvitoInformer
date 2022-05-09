package telegram;

import charts.TimeSeriesChart;
import db.FlatDAO;
import model.Data;
import model.FlatAvito;
import model.Periods;
import model.Yaxis;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MessageReciever implements Runnable {
    private BotTG bot;
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;


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
        String chatId = String.valueOf(message.getChatId());
        User user = Data.getUserById(Long.valueOf(chatId));
        if (user == null){
            String username = message.getFrom().getFirstName()+" "+message.getFrom().getLastName();
            user = new User(Long.valueOf(chatId),username);
            Data.settings.add(user);
        }

        String finalInputText = inputText;

        if(inputText.equals("/start")){
            user.getCity().clear();//////////////////////////////
        } else if (inputText.equals("/set_city")){
            user.getCity().clear();
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessageCity(chatId));
        }else if (inputText.equals("/add_city")) {
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessageCity(chatId));
        }else if (inputText.equals("/set_period")){
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessagePeriod(chatId));
        }
        else if (inputText.equals("/set_parameter")){
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessageYaxis(chatId));
        }else if(inputText.equals("/get")) {
            StringBuilder stringBuilder = new StringBuilder("Открой меню бота и :");
            boolean isSendPhoto = true;
            if (user.getCity().isEmpty()){
                stringBuilder.append("Выбери город");
                isSendPhoto = false;

            }if (user.getPeriod() == null){
                stringBuilder.append("Выбери период");
                isSendPhoto = false;

            }if (user.getParameter() == null){
                stringBuilder.append("Выбери параметр");
                isSendPhoto = false;

            }if (stringBuilder.length()>25){
                bot.sendQueue.add(MessageTG.sendMyMessage(chatId, String.valueOf(stringBuilder)));
            }
            if (isSendPhoto) {
                try {
                    SendPhoto photoMessage = new SendPhoto();
                    photoMessage.setPhoto(TimeSeriesChart.getJpeg(user.getCity(), user.getParameter()));
                    photoMessage.setChatId(chatId);
                    bot.sendQueue.add(photoMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // City
        else if (FlatAvito.link.keySet().contains(inputText)){
            user.getCity().add(inputText);
        }
        // City
        else if (inputText.equals("All City")){
            user.setCity(FlatAvito.link.keySet());
        }
        // Parameter
        else if (Arrays.stream(Yaxis.values()).anyMatch(x->x.toString().equals(finalInputText))){
            user.setParameter(Yaxis.valueOf(inputText));
        }
        // Period
        else if (Arrays.stream(Periods.values()).anyMatch(x->x.toString().equals(finalInputText))){
            user.setPeriod(Periods.valueOf(inputText));
        }
        // Top 20
        else if(inputText.equals("Top")){
            // Нужно вывести только имеющиеся
            FlatDAO flatDAO = new FlatDAO();
            try {
                List<String> listTop = flatDAO.getTopList(user.getCity());
                for (String s : listTop) {
                    bot.sendQueue.add(MessageTG.sendMyMessage(chatId,s));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
