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
        ScheduleEvent[] events = FileManager.jsonParse(FileManager.readFile("user1.json"));

        for (ScheduleEvent event : events) {
            result.append("\n");
            result.append(event.toString());
        }

        return result.toString();
    }
}
