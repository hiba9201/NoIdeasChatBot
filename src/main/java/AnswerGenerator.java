import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AnswerGenerator {
    private Map<String, String[]> lineStorage = new HashMap<>();

    public AnswerGenerator() {
        lineStorage.put("/start",
                new String[]{"Привет, я помогу составить твое расписание. Чтобы узнать больше, введи /help"});
        lineStorage.put("/help", new String[]{"Я бот, помогающий составить расписание. Вот что я могу:\n" +
                "Показать все события - /show [period]\n" +
                "period может быть day, week или month. Если периода нет, то показываются все события\n" +
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

    public String generateAnswerByLine(String line, int modeStep, Long chatId) {
        if (!this.lineStorage.containsKey(line.split(" ")[0])) {
            return this.lineStorage.get("/else")[0];
        }

        if (this.isDialogFinished(modeStep, line.split(" ")[0])) {
            return "Что-то пошло не так(";
        }

        String answer = this.lineStorage.get(line.split(" ")[0])[modeStep];

        switch (line.trim()) {
            case "/show":
                return answer + this.generateAllEventsList(chatId, Periods.getPeriod(TimeInterval.ALL));
            case "/show day":
                return answer + this.generateAllEventsList(chatId, Periods.getPeriod(TimeInterval.DAY));
            case "/show week":
                return answer + this.generateAllEventsList(chatId, Periods.getPeriod(TimeInterval.WEEK));
            case "/show month":
                return answer + this.generateAllEventsList(chatId, Periods.getPeriod(TimeInterval.MONTH));
            default:
                return answer;
        }
    }

    public String generateAllEventsList(Long chatId, Period period) {
        StringBuilder result = new StringBuilder();
        CalendarManager userFile = new CalendarManager();
        ComponentList events;
        try {
            events = userFile.getCalendarEvents(chatId, period);
        } catch (EmptyCalendarException e) {
            return "\nНет событий в текущем периоде :(\nЧтобы добавить событие, введите /add";
        }

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
