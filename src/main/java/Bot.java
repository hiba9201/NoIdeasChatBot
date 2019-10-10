import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.logging.Level;

public class Bot extends TelegramLongPollingBot {
    private DialogLogic logic = new DialogLogic();

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        String newMessage = this.logic.executeCommand(message);
        sendMsg(update.getMessage().getChatId().toString(), newMessage);
    }

    private void sendMsg(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
//            log.log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    @Override
    public String getBotUsername() {
        return "TimeManagementChatBot";
    }

    @Override
    public String getBotToken() {
        return "876468220:AAGC_y5n1vezrZ2CY1x89OmK5_wPz5xpZoo";
    }
}
