import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class AnswerGenerator {
    private Map<String, String[]> lineStorage = new HashMap<>();
    public DialogMode currentMode = DialogMode.DEFAULT;
    private int modeStep = 0;
    private AdditionEvent event = new AdditionEvent();

    public AnswerGenerator() {
        lineStorage.put("/start",
                new String[]{"Привет, я помогу составить твое расписание. Чтобы узнать больше, введи /help"});
        lineStorage.put("/help", new String[]{"Я бот, помогающий составить расписание. Вот что я могу:\n" +
                "Показать все события - /show\n" +
                "Вывести это сообщение - /help\n" +
                "Добавить событие - /add"});
        lineStorage.put("/show", new String[]{"Вот твое расписание:"});
        lineStorage.put("/else", new String[]{"Я существую и не понимаю"});
        lineStorage.put("/add", new String[]{"Введите название события:",
                                             "Введите дату события в формате dd.MM.yyyy:",
                                             "Введите время события в формате HH:mm:",
                                             "Введите описание события"});
    }

    public String generateAnswerByLine(String line) {
        if (currentMode == DialogMode.ADD) {
            // делаем что-то с поданной строкой
            if (modeStep == 0) {
                this.event.setName("Event name");
            }
            if (modeStep == 1) {
                this.event.setDate("16.10.2019");
            }
            if (modeStep == 2) {
                this.event.setTime("14:15");
            }
            if (modeStep == 3) {
                this.event.setDescription("Description");
            }

            String[] answersArray = this.lineStorage.get(line);
            modeStep += 1;
            if (modeStep >= answersArray.length) {
                currentMode = DialogMode.DEFAULT;
                Calendar calendar = FileManager.addEvent(FileManager.getCalendar("0000.ics"), event);
                try {
                    FileManager.saveCalendar(calendar, "0000.ics");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.event = new AdditionEvent();
                return "Готово!";
            } else {
                return answersArray[modeStep];
            }
        }

        if (!this.lineStorage.containsKey(line)) {
            return this.lineStorage.get("/else")[0];
        }

        String answer = this.lineStorage.get(line)[0];

        switch (line) {
            case "/show":
                return answer + this.generateAllEventsList();
            case "/add":
                currentMode = DialogMode.ADD;
                modeStep = 0;
                return answer;
            default:
                return answer;
        }
    }

    public String generateAllEventsList() {
        StringBuilder result = new StringBuilder();
        ComponentList events = FileManager.getCalendarEvents(FileManager.getCalendar("0000.ics"));

        for (Object elem : events) {
            VEvent event = (VEvent) elem;
            String description = event.getDescription().getValue();
            String title = event.getSummary().getValue();
            String pattern = "dd.MM.yyyy HH:mm";
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            String date = dateFormat.format(event.getStartDate().getDate());

            result.append(String.format("\n%s     %s : %s", date, title, description));
        }
        
        return result.toString();
    }
}
