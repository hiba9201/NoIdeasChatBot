import org.junit.Test;

import static org.junit.Assert.*;

public class TestAnswerGenerator {
    private AnswerGenerator generator = new AnswerGenerator();

    @Test
    public void testNotCommandInput() {
        assertEquals("Я существую и не понимаю", generator.generateAnswerByLine("ojlksamdlk", 0));
    }

    @Test
    public void testShow() {
        assertEquals("Вот твое расписание:" + generator.generateAllEventsList(),
                     generator.generateAnswerByLine("/show", 0));
    }

    @Test
    public void testFinishedDialog() {
        assertEquals("Что-то пошло не так(", generator.generateAnswerByLine("/add", 4));
    }
}
