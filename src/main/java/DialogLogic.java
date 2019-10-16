import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;

public class DialogLogic {
    private AnswerGenerator answerGenerator = new AnswerGenerator();
    private DialogMode currentMode = DialogMode.DEFAULT;
    private int modeStep = 0;
    private AdditionEvent event = new AdditionEvent();

    public String executeCommand(String command) {
        FileManager user = new FileManager("0000");
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

                    try {
                        user.addEvent(this.event);
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
