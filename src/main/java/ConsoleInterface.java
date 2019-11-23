import java.util.Scanner;

public class ConsoleInterface {
    private DialogLogic logic = new DialogLogic();
    private Scanner scan = new Scanner(System.in);

    public void start() {
        System.out.println(this.logic.executeCommand("/start", (long) 0));

        while (true) {
            String input = scan.next();
            System.out.println(this.logic.executeCommand(input, (long) 0));
            if (input.equals("/exit")) {
                break;
            }
        }
    }
}
