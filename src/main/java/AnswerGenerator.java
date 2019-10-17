import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class AnswerGenerator {
    private Map<String, String[]> lineStorage = new HashMap<>();

    public AnswerGenerator() {
        lineStorage.put("/start",
                new String[]{"Привет, я помогу составить твое расписание. Чтобы узнать больше, введи /help"});
        lineStorage.put("/help", new String[]{"Я бот, помогающий составить расписание. Вот что я могу:\n" +
                "Показать все события - /show\n" +
                "Вывести это сообщение - /help\n" +
                "Добавить событие - /add"});
        lineStorage.put("/show", new String[]{"Вот твое расписание:"});
        lineStorage.put("/else", new String[]{"Я существую и не понимаю"});
        lineStorage.put("/add", new String[]{"Введите название события",
                                             "Введите дату события в формате dd.MM.yyyy",
                                             "Введите время события в формате HH:mm",
                                             "Введите описание события"});
    }

    private boolean isDialogFinished(int modeStep, String command) {
        return modeStep >= this.lineStorage.get(command).length;
    }

    public String generateAnswerByLine(String line, int modeStep) {

        if (!this.lineStorage.containsKey(line)) {
            return this.lineStorage.get("/else")[0];
        }

        if (this.isDialogFinished(modeStep, line)) {
            return "Что-то пошло не так(";
        }

        String answer = this.lineStorage.get(line)[modeStep];

        switch (line) {
            case "/show":
                return answer + this.generateAllEventsList();
            default:
                return answer;
        }
    }

    public String generateAllEventsList() {
        StringBuilder result = new StringBuilder();
        FileManager userFile = new FileManager("0000");
        ComponentList events = userFile.getCalendarEvents();

        for (Object elem : events) {
            VEvent event = (VEvent) elem;
            String description = event.getDescription().getValue();
            String title = event.getSummary().getValue();
            String pattern = "dd.MM.yyyy HH:mm";
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            String date = dateFormat.format(event.getStartDate().getDate());

            result.append(String.format("\n%s   %s : %s", date, title, description));
        }
        
        return result.toString();
    }
}
