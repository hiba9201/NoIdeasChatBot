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

    public Calendar addEvent(AdditionEvent additionEvent) throws IOException {
        Calendar calendar;
        try {
            calendar = this.getCalendar();
        } catch (EmptyCalendarException e) {
            calendar = this.createCalender(new File(this.filePath));
        }
        assert calendar != null;

        VEvent event = createVEvent(additionEvent);
        calendar.getComponents().add(event);
        this.saveCalendar(calendar);

        return calendar;
    }

    private VEvent createVEvent(AdditionEvent additionEvent) {
        String eventName = additionEvent.getName();
        DateTime start = new DateTime(additionEvent.getDate().getTime());
        VEvent event = new VEvent(start, eventName);

        event.getProperties().add(new Description(additionEvent.getDescription()));
        event.getProperties().add(new Uid(this.userID));

        return event;
    }

    private void saveCalendar(Calendar calendar) throws IOException {
        ComponentList events = calendar.getComponents();
        File file = new File(this.filePath);

        if (events.isEmpty()) {
            file.delete();
        }

        if (!file.exists()) {
            calendar = this.createCalender(new File(this.filePath));
        }

        CalendarOutputter outputter = new CalendarOutputter();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
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

    public Calendar getCalendar() throws EmptyCalendarException {
        File file = new File(this.filePath);
        if (!file.exists()) {
            throw new EmptyCalendarException("В календаре отсутствуют события", this.userID);
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

    public ComponentList getCalendarEvents() throws EmptyCalendarException {
        return this.getCalendar().getComponents(Component.VEVENT);
    }
}
