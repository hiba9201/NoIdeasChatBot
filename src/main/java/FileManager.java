import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import java.io.*;


public class FileManager {
    private String userID;
    private String filePath;

    public FileManager(String userID) {
        this.userID = userID;
        this.filePath = "src" + File.separator + "main" + File.separator +
                "resources" + File.separator + this.userID + ".ics";
    }

    public Calendar addEvent(AdditionEvent event) throws IOException {
        Calendar calendar = null;

        File file = new File(filePath);
        if (!file.exists()) {
            calendar = this.createCalender(file);
        } else {
            calendar = this.getCalendar();
        }
        assert calendar != null;

        String eventName = event.getName();

        java.util.Calendar startDate = event.getDate();
        DateTime start = new DateTime(startDate.getTime());

        VEvent meeting = new VEvent(start, eventName);

        meeting.getProperties().add(new Description(event.getDescription()));
        meeting.getProperties().add(new Uid(this.userID));

        calendar.getComponents().add(meeting);
        this.saveCalendar(calendar);
        return calendar;
    }

    private void saveCalendar(Calendar calendar) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            // Кидать ошибку, что календарь пуст
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        CalendarOutputter outputter = new CalendarOutputter();
        try {
            outputter.output(calendar, fileOutputStream);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private Calendar createCalender(File file) throws IOException {
        file.createNewFile();
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//timeManagementChatBot"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        return calendar;
    }

    public Calendar getCalendar() {
        File file = new File(filePath);
        if (!file.exists()) {
            // Кидать ошибку, что календарь пуст
        }

        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = null;

        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            calendar = builder.build(inputStream);
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }

        assert calendar != null;
        return calendar;
    }

    public ComponentList getCalendarEvents() {
        Calendar calendar = getCalendar();
        return calendar.getComponents(Component.VEVENT);
    }
}
