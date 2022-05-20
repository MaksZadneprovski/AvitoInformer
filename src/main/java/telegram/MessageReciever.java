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
import java.util.HashSet;
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
        System.out.println(user);
        System.out.println(chatId);
        if (user == null){
            String fname = message.getFrom().getFirstName();
            String lname = message.getFrom().getLastName();
            StringBuilder username = new StringBuilder();
            username.append(fname);
            if (lname!=null) username.append(" ").append(lname);
            user = new User(Long.valueOf(chatId),username.toString());
            Data.settings.add(user);
        }
        String finalInputText = inputText;

        if(inputText.equals("/start")){
            bot.sendQueue.add(MessageTG.sendStartMessage(user));
        }else if (inputText.equals("/set_city")){
            user.getCity().removeAll(Data.link.keySet());
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessageCity(chatId));
        }else if (inputText.equals("/add_city")) {
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessageCity(chatId));
        }else if (inputText.equals("/set_period")){
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessagePeriod(chatId));
        }
        else if (inputText.equals("/set_parameter")){
            bot.sendQueue.add(MessageTG.sendInlineKeyBoardMessageYaxis(chatId));
        }else if(inputText.equals("/get")) {
            StringBuilder stringBuilder = new StringBuilder("Открой меню бота и :\n");
            boolean isSendPhoto = true;
            if (user.getCity().isEmpty()){
                stringBuilder.append("Выбери город\n");
                isSendPhoto = false;

            }if (user.getPeriod() == null){
                stringBuilder.append("Выбери временной период\n");
                isSendPhoto = false;

            }if (user.getParameter() == null){
                stringBuilder.append("Выбери тип графика\n");
                isSendPhoto = false;

            }if (stringBuilder.length()>25){
                bot.sendQueue.add(MessageTG.sendMyMessage(chatId, String.valueOf(stringBuilder)));
            }
            if (isSendPhoto) {
                try {
                    SendPhoto photoMessage = new SendPhoto();
                    photoMessage.setPhoto(TimeSeriesChart.getJpeg(user.getCity(), user.getParameter(), user.parseParameter(), user.getPeriod()));
                    photoMessage.setChatId(chatId);
                    bot.sendQueue.add(photoMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (inputText.equals("/get_parameters")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Город : ").append(user.getCity()).append("\n\nПериод : ").append(user.parsePeriod()).append("\n\nТип : ").append(user.parseParameter());
            bot.sendQueue.add(MessageTG.sendMyMessage(chatId,sb.toString()));
        }
        else if (inputText.equals("/get_top")){
            FlatDAO flatDAO = new FlatDAO();
            if (user.getCity().size()>0) {
                try {
                    List<String> listTop = flatDAO.getTopList(user.getCity());
                    for (String s : listTop) {
                        bot.sendQueue.add(MessageTG.sendMyMessage(chatId, s));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else {
                bot.sendQueue.add(MessageTG.sendMyMessage(chatId,"Сначала выбери город"));
            }
        }


        // City
        else if (Data.link.containsKey(inputText)){
            if (user.getCity().stream().noneMatch(x ->x.equals(finalInputText))) {
                user.getCity().add(inputText);
            }
            bot.sendQueue.add(MessageTG.sendMyMessage(chatId, "Город добавлен"));
        }
        // City
        else if (inputText.equals("All City")){
            user.setCity(new HashSet<>(Data.link.keySet()));
            bot.sendQueue.add(MessageTG.sendMyMessage(chatId, "Города добавлены"));
        }
        // Parameter
        else if (Arrays.stream(Yaxis.values()).anyMatch(x->x.toString().equals(finalInputText))){
            user.setParameter(Yaxis.valueOf(inputText));
            bot.sendQueue.add(MessageTG.sendMyMessage(chatId, "Параметр установлен"));
        }
        // Period
        else if (Arrays.stream(Periods.values()).anyMatch(x->x.toString().equals(finalInputText))){
            user.setPeriod(Periods.valueOf(inputText));
            bot.sendQueue.add(MessageTG.sendMyMessage(chatId, "Период установлен"));
        }
        else if (inputText.equals("difference")){
            bot.sendQueue.add(MessageTG.sendMyMessage(chatId, "" +
                    "Среднее арифметическое набора данных находится суммированием всех чисел в выборке и делением полученной суммы на количество чисел.\n\n" +
                    "Медиана — это число, которое окажется строго по центру списка чисел в наборе данных, если их предварительно упорядочить по возрастанию."));
        }
    }
}
