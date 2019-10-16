import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import java.io.*;
import java.util.GregorianCalendar;


public class FileManager {
    private String userID;

    public FileManager(String userID) {
        this.userID = userID;
    }

    public void addEvent(AdditionEvent event) throws IOException {
        Calendar calendar = this.getCalendar();
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

        meeting.getProperties().add(new Uid(this.userID));
        //Нужно знать id пользователя

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
        String path = FileManager.class.getResource(this.userID + ".ics").getPath();

        try (FileInputStream inputStream = new FileInputStream(path)){
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
