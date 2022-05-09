import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import telegram.BotTG;
import telegram.MessageReciever;
import telegram.MessageSender;

public class App {
    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        BotTG botTG = new BotTG("ZadneprovskiBot", "2117503517:AAGLyu2PukHxITGpWR2pw8tcVBrnQ3ejE-4");
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
