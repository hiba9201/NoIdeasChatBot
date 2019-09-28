import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class AnswerGenerator {
    private Map<String, String> lineStorage = new HashMap<>();

    public AnswerGenerator() {
        lineStorage.put("/start", "Привет, я помогу составить твое расписание. Чтобы узнать больше, введи /help");
        lineStorage.put("/help", "Я бот, помогающий составить расписание. Вот что я могу:\n" +
                "Показать все события - /show\n" +
                "Вывести это сообщение - /help\n" +
                "Выйти из диалога - /exit");
        lineStorage.put("/show", "Вот твое расписание:");
        lineStorage.put("/else", "Я существую и не понимаю");
        lineStorage.put("/exit", "До скорых встреч!");
    }

    public String generateAnswerByLine(String line) {
        if (!this.lineStorage.containsKey(line)) {
            return this.lineStorage.get("/else");
        }
        String answer = this.lineStorage.get(line);
        switch (line) {
            case "/show":
                return answer + this.generateAllEventsList();
            default:
                return answer;
        }
    }

    public String generateAllEventsList() {
        StringBuilder result = new StringBuilder();
        ComponentList events = FileManager.parseIcsFormat("0000.ics");

        for (Object elem : events) {
            VEvent event = (VEvent) elem;
            String description = event.getDescription().getValue();
            String title = event.getSummary().getValue();
            String pattern = "MM/dd/yyyy HH:mm:ss";
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            String date = dateFormat.format(event.getStartDate().getDate());

            result.append(String.format("\n%s     %s : %s", date, title, description));
        }
        
        return result.toString();
    }
}
