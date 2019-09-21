package main.java;

import java.util.Scanner;

public class ConsoleInterface {
    private DialogLogic logic = new DialogLogic(new Storage());
    private Scanner scan = new Scanner(System.in);

    public void start() {
        this.logic.executeCommand("/start");
        while (true) {
            String input = scan.next();
            this.logic.executeCommand(input);
        }
    }
}
