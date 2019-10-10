public class DialogLogic {
    private AnswerGenerator answerGenerator = new AnswerGenerator();

    public String executeCommand(String command) {
        String answer;
        if (answerGenerator.currentMode == DialogMode.ADD) {
            answer = this.answerGenerator.generateAnswerByLine("/add");
        }
        else {
            answer = this.answerGenerator.generateAnswerByLine(command);
        }

        return answer;
    }
}
