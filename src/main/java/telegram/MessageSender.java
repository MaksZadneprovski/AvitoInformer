package telegram;


import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class MessageSender implements Runnable {
    private final int SENDER_SLEEP_TIME = 1000;
    private BotTG bot;

    public MessageSender(BotTG bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        while (true) {
            for (Object object = bot.sendQueue.poll(); object != null; object = bot.sendQueue.poll()) {
                send(object);
            }
            try {
                Thread.sleep(SENDER_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(Object object) {
        try {
            MessageType messageType = messageType(object);
            switch (messageType) {
                case EXECUTE_MESSAGE:
                    SendMessage message = (SendMessage) object;
                    bot.execute(message);
                    break;
                case EXECUTE_PHOTO:
                    SendPhoto message2 = (SendPhoto) object;
                    bot.execute(message2);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MessageType messageType(Object object) {
        if (object instanceof BotApiMethod) return MessageType.EXECUTE_MESSAGE;
        if (object instanceof PartialBotApiMethod) return MessageType.EXECUTE_PHOTO;
        return MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE_MESSAGE,EXECUTE_PHOTO, NOT_DETECTED,
    }
}
