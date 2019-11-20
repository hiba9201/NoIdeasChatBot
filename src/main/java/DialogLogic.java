import java.text.ParseException;

public class DialogLogic {
    private AnswerGenerator answerGenerator = new AnswerGenerator();
    private DialogMode currentMode = DialogMode.DEFAULT;
    private int modeStep = 0;
    private AdditionEvent event = new AdditionEvent();

    public String executeCommand(String command, Long chatId) {
        CalendarManager db = new CalendarManager();
        if (this.currentMode == DialogMode.ADD) {
            switch (this.modeStep) {
                case 0:
                    this.event.setName(command);
                    break;
                case 1:
                    try {
                        this.event.setDate(command);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return this.answerGenerator.generateAnswerByLine("/add", modeStep, chatId);
                    }
                    break;
                case 2:
                    try {
                        this.event.setTime(command);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return this.answerGenerator.generateAnswerByLine("/add", modeStep, chatId);
                    }
                    break;
                case 3:
                    this.event.setDescription(command);

                    this.currentMode = DialogMode.DEFAULT;
                    this.modeStep = 0;

                    db.addEvent(this.event, chatId);

                    this.event = new AdditionEvent();

                    return "Готово!";
                default:
                    break;
            }
            this.modeStep++;
            command = "/add";
        }
        else if (command.equals("/add")) {
            this.currentMode = DialogMode.ADD;
        }

        return this.answerGenerator.generateAnswerByLine(command, modeStep, chatId);
    }

    public DialogMode getDialogMode() {
        return currentMode;
    }
}
