package main.java;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private Map<String, String> lineStorage = new HashMap<>();

    public Storage() {
        lineStorage.put("/start", "Привет, я помогу составить твое расписание. Чтобы узнать больше, введи \"/help\"");
        lineStorage.put("/help", "Я бот, помогающий составить расписание. Вот что я могу:\n" +
                "Показать все события - \"/show\"\n" +
                "Вывести это сообщение - \"/help\"\n" +
                "Выйти из диалога - \"/exit\"");
        lineStorage.put("/show", "Вот твое расписание:\n");
        lineStorage.put("/else", "Я существую, но не понимаю");
        lineStorage.put("/exit", "До скорых встреч!");
    }

    public String getAnswerByLine(String line) {
        if (this.lineStorage.containsKey(line)) {
            return this.lineStorage.get(line);
        } else {
            return this.lineStorage.get("/else");
        }
    }
}
