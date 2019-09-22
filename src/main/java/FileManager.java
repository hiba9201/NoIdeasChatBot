import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class FileManager {
    private ArrayList<User> users = new ArrayList<User>();

    public FileManager() {
    }

    public static String readFile(String fileName) {
        File file = new File(fileName);
        StringBuilder lines = new StringBuilder();
        Scanner scanner = new Scanner(System.in);

        try {
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                lines.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

        return lines.toString();
    }

    public static ScheduleEvent[] jsonParse(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, ScheduleEvent[].class);
    }
}
