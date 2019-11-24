import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDialogLogic {
    private DialogLogic dialogLogic = new DialogLogic();
    private AnswerGenerator generator = new AnswerGenerator();

    @Test
    public void testAddLogic() {
        String answer = dialogLogic.executeCommand("/add", (long) 0);
        assertEquals(answer, generator.generateAnswerByLine("/add", 0, (long) 0));

        answer = dialogLogic.executeCommand("Событие", (long) 0);
        assertEquals(answer, generator.generateAnswerByLine("/add", 1, (long) 0));

        answer = dialogLogic.executeCommand("12.12.2019", (long) 0);
        assertEquals(answer, generator.generateAnswerByLine("/add", 2, (long) 0));

        answer = dialogLogic.executeCommand("13:13", (long) 0);
        assertEquals(answer, generator.generateAnswerByLine("/add", 3, (long) 0));

        answer = dialogLogic.executeCommand("Описание", (long) 0);
        assertEquals(answer, "Готово!");
    }

    @Test
    public void testEventAdded() {
        dialogLogic.executeCommand("/add", (long) 0);
        dialogLogic.executeCommand("Событие", (long) 0);
        dialogLogic.executeCommand("12.12.2019", (long) 0);
        dialogLogic.executeCommand("10:45", (long) 0);
        dialogLogic.executeCommand("Описание", (long) 0);
        String show = dialogLogic.executeCommand("/show", (long) 0);
        assertTrue(show.contains("12.12.2019 10:45   Событие : Описание"));
    }

    @Test
    public void testAddValidation() {
        String answer = dialogLogic.executeCommand("/add", (long) 0);
        assertEquals(answer, generator.generateAnswerByLine("/add", 0, (long) 0));

        answer = dialogLogic.executeCommand("Событие", (long) 0);
        assertEquals(answer, generator.generateAnswerByLine("/add", 1, (long) 0));

        answer = dialogLogic.executeCommand("adasd", (long) 0);
        assertEquals(answer, generator.generateAnswerByLine("/add", 1, (long) 0));
    }
}
