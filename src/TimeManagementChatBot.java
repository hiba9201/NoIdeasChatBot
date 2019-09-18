import java.util.Scanner;

public class TimeManagementChatBot {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Привет, я бот, который может хранить твоё расписание");
        while (true)
        {
            String input = scan.next();
            if (input.equals("/help")){
                System.out.println("Со мной пока нельзя ничего делать");
            }
            else if (input.equals("/exit")) {
                break;
            }
            else if (input.length() != 0) {
                System.out.println("Я существую");
            }
        }
    }
}
