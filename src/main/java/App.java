import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;

public class App {
    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        BotTG test_habr_bot = new BotTG("ZadneprovskiBot", "2117503517:AAGLyu2PukHxITGpWR2pw8tcVBrnQ3ejE-4");
        test_habr_bot.botConnect();
    }
}
