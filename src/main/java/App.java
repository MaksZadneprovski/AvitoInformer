import model.Data;
import telegram.BotTG;
import telegram.MessageReciever;
import telegram.MessageSender;

public class App {

    public static void main(String[] args) {
        Data.updateLinks();
        BotTG botTG = new BotTG("EstatePriceBot" , "5126773688:AAGDF2Fai5tyIa8-sbFOSWwDoRembo4UAuo");
        MessageReciever messageReciever = new MessageReciever(botTG);
        MessageSender messageSender = new MessageSender(botTG);
        botTG.botConnect();


        Thread receiver = new Thread(messageReciever);
        receiver.setDaemon(true);

        receiver.setName("MsgReciever");
        receiver.start();

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");sender.start();


    }
}
