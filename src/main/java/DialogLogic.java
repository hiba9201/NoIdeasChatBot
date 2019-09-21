package main.java;

public class DialogLogic {
    private Storage storage;

    public DialogLogic(Storage storage) {
        this.storage = storage;
    }

    public String executeCommand(String command) {
        var standardAnswer = this.storage.getAnswerByLine(command);
        switch (command) {
            case "/show":
                return standardAnswer;
            default:
                return standardAnswer;
        }
    }
}
