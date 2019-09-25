import org.junit.Test;

import static org.junit.Assert.*;

public class TestAnswerGenerator {
    private AnswerGenerator generator = new AnswerGenerator();

    @Test
    public void testNotCommandInput() {
        assertEquals("Я существую и не понимаю", generator.generateAnswerByLine("ojlksamdlk"));
    }

    @Test
    public void testAllEventsGeneration() {
        String schedule = "\nДень рождения?)                Дата: 29.11.2019 00:00\n" +
                "Встреча с подруШками           Дата: 30.09.2019 17:00\n" +
                "Урок игры на гитаре            Дата: 30.09.2019 20:00\n" +
                "Хакатон                        Дата: 19.10.2019 17:30\n" +
                "Сдача питона                   Дата: 18.09.2019 11:30\n" +
                "База                           Дата: 12.10.2019 14:00";
        assertEquals(schedule, generator.generateAllEventsList());
    }

    @Test
    public void testShow() {
        assertEquals("Вот твое расписание:" + generator.generateAllEventsList(),
                     generator.generateAnswerByLine("/show"));
    }
}
