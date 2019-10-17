import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDialogLogic {
    private DialogLogic dialogLogic = new DialogLogic();
    private AnswerGenerator generator = new AnswerGenerator();

    @Test
    public void testAddLogic() {
        String answer = dialogLogic.executeCommand("/add");
        assertEquals(answer, generator.generateAnswerByLine("/add", 0));

        answer = dialogLogic.executeCommand("Событие");
        assertEquals(answer, generator.generateAnswerByLine("/add", 1));

        answer = dialogLogic.executeCommand("12.12.2019");
        assertEquals(answer, generator.generateAnswerByLine("/add", 2));

        answer = dialogLogic.executeCommand("13:13");
        assertEquals(answer, generator.generateAnswerByLine("/add", 3));

        answer = dialogLogic.executeCommand("Описание");
        assertEquals(answer, "Готово!");
    }

    @Test
    public void testAddValidation() {
        String answer = dialogLogic.executeCommand("/add");
        assertEquals(answer, generator.generateAnswerByLine("/add", 0));

        answer = dialogLogic.executeCommand("Событие");
        assertEquals(answer, generator.generateAnswerByLine("/add", 1));

        answer = dialogLogic.executeCommand("adasd");
        assertEquals(answer, generator.generateAnswerByLine("/add", 1));
    }
}
