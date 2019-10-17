import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import java.io.*;


public class FileManager {
    private String userID;

    public FileManager(String userID) {
        this.userID = userID;
    }

    public void addEvent(AdditionEvent event) throws IOException {
        Calendar calendar = this.getCalendar();
        String eventName = event.getName();

        java.util.Calendar startDate = event.getDate();
        DateTime start = new DateTime(startDate.getTime());

        VEvent meeting = new VEvent(start, eventName);

        meeting.getProperties().add(new Description(event.getDescription()));

        meeting.getProperties().add(new Uid(this.userID));

        calendar.getComponents().add(meeting);
        this.saveCalendar(calendar);
    }

    private void saveCalendar(Calendar calendar) throws IOException {
        File file = new File("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + this.userID + ".ics");
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

    public void createCalender() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//timeManagementChatBot"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
    }

    public Calendar getCalendar() {
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = null;
        String path = "src" + File.separator + "main" + File.separator +
                "resources" + File.separator + this.userID + ".ics";

        try (FileInputStream inputStream = new FileInputStream(path)) {
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
