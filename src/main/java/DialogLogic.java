package main.java;

public class DialogLogic {
    private Storage storage;

    public DialogLogic(Storage storage) {
        this.storage = storage;
    }

    public void generateAnswerByLine(String line) {
        String standardAnswer = this.storage.getAnswerByLine(line);
        switch (line) {
            case "/show":
                System.out.println(standardAnswer);
                break;
            case "/exit":
                System.exit(0);
                break;
            default:
                System.out.println(standardAnswer);
                break;
        }
    }
}
