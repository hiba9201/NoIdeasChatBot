import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import java.io.*;
import java.net.URISyntaxException;
import java.util.GregorianCalendar;


public class FileManager {

    public FileManager() {
    }

    public static Calendar addEvent(Calendar calendar, String event) {

        java.util.Calendar startDate = new GregorianCalendar();
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.NOVEMBER);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 10);
        startDate.set(java.util.Calendar.YEAR, 2019);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 19);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        // Создаем событие
        String eventName = "Какое-то мероприятие";
        DateTime start = new DateTime(startDate.getTime());
        VEvent meeting = new VEvent(start, eventName);
        meeting.getProperties().add(new Description("Aaaa"));
        meeting.getProperties().add(new Uid("aaa"));

        calendar.getComponents().add(meeting);
        return calendar;
    }

    public static void saveCalendar(Calendar calendar, String fileName) throws IOException {
        //String path = ".." + File.separator + "resources" + File.separator + fileName;
        String path = fileName;
        File calendarFile = new File(path);
        if (!calendarFile.exists()) {
            //calendarFile.getParentFile().mkdirs();
            calendarFile.createNewFile();
        }
        FileOutputStream fout = new FileOutputStream(path);
        CalendarOutputter out = new CalendarOutputter();
        try {
            out.output(calendar, fout);
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
