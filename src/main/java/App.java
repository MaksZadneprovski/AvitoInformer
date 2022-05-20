import telegram.BotTG;
import telegram.MessageReciever;
import telegram.MessageSender;

public class App {

    public static void main(String[] args) {
        System.out.println("start");
        BotTG botTG = new BotTG("EstatePriceBot" , "5126773688:AAGDF2Fai5tyIa8-sbFOSWwDoRembo4UAuo");
        MessageReciever messageReciever = new MessageReciever(botTG);
        MessageSender messageSender = new MessageSender(botTG);
        botTG.botConnect();

        System.out.println(botTG);

        Thread receiver = new Thread(messageReciever);
        receiver.setDaemon(true);

        receiver.setName("MsgReciever");
        receiver.start();
        System.out.println(receiver);

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");sender.start();
        System.out.println(sender);
    }
}
