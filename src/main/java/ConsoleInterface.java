import java.util.Scanner;

public class ConsoleInterface {
    private DialogLogic logic = new DialogLogic();
    private Scanner scan = new Scanner(System.in);

    public void start() {
        System.out.println(this.logic.executeCommand("/start"));

        while (true) {
            String input = scan.next();
            System.out.println(this.logic.executeCommand(input));
            if (input.equals("/exit")) {
                break;
            }
        }
    }
}
