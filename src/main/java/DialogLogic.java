import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;

public class DialogLogic {
    private AnswerGenerator answerGenerator = new AnswerGenerator();
    private DialogMode currentMode = DialogMode.DEFAULT;
    private int modeStep = 0;
    private AdditionEvent event = new AdditionEvent();

    public String executeCommand(String command) {

        if (this.currentMode == DialogMode.ADD) {
            switch (this.modeStep) {
                case 0:
                    this.event.setName(command);
                    break;
                case 1:
                    this.event.setDate(command);
                    break;
                case 2:
                    this.event.setTime(command);
                    break;
                case 3:
                    this.event.setDescription(command);

                    this.currentMode = DialogMode.DEFAULT;
                    this.modeStep = 0;
                    Calendar calendar = FileManager.addEvent(FileManager.getCalendar("0000.ics"), this.event);
                    try {
                        FileManager.saveCalendar(calendar, "0000.ics");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

        return this.answerGenerator.generateAnswerByLine(command, modeStep);
    }
}
