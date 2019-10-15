import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import java.io.*;
import java.util.GregorianCalendar;


public class FileManager {

    public FileManager() {
    }

    public static Calendar addEvent(Calendar calendar, AdditionEvent event) {
        String eventName = "Встреча с подрушшками";
        //String eventName = event.getName();

        java.util.Calendar startDate = new GregorianCalendar();
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 16);
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.OCTOBER);
        startDate.set(java.util.Calendar.YEAR, 2019);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 19);
        startDate.set(java.util.Calendar.MINUTE, 30);
        startDate.set(java.util.Calendar.SECOND, 0);
        DateTime start = new DateTime(startDate.getTime());
        //Date start = event.getDate();

        VEvent meeting = new VEvent(start, eventName);

        meeting.getProperties().add(new Description("Ура!"));
        //meeting.getProperties().add(new Description(event.getDescription()));

        meeting.getProperties().add(new Uid("0000"));
        //Нужно знать id пользователя

        calendar.getComponents().add(meeting);
        return calendar;
    }

    public static void saveCalendar(Calendar calendar, String fileName) throws IOException {
        File file = new File("src" + File.separator + "main" + File.separator +
                              "resources" + File.separator + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        CalendarOutputter outputter = new CalendarOutputter();
        try {
            outputter.output(calendar, fileOutputStream);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static void createCalender() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//timeManagementChatBot"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
    }

    static Calendar getCalendar(String fileName) {
        CalendarBuilder builder = new CalendarBuilder();

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(FileManager.class.getResource(fileName).getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert inputStream != null;

        Calendar calendar = null;
        try {
            calendar = builder.build(inputStream);
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }
        assert calendar != null;

        return calendar;
    }

    static ComponentList getCalendarEvents(Calendar calendar) {
        return calendar.getComponents(Component.VEVENT);
    }
}
