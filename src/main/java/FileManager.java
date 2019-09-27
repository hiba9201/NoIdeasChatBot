import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


public class FileManager {
    private Map<String, ComponentList> users = new HashMap<>();

    public FileManager() {
    }

    public static void createCalender() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//habrahabr"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
    }

    static ComponentList parseEventList(User user) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(FileManager.class.getResource(user.getScheduleFileName()).getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert inputStream != null;

        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = null;
        try {
            calendar = builder.build(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert calendar != null;

        return calendar.getComponents(Component.VEVENT);
    }
}
