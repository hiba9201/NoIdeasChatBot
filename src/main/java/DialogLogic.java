public class DialogLogic {
    private AnswerGenerator answerGenerator = new AnswerGenerator();

    public String executeCommand(String command) {
        String answer = this.answerGenerator.generateAnswerByLine(command);

        return answer;
    }
}
