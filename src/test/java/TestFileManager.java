import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.GregorianCalendar;


public class TestFileManager {
    private FileManager userFile;
    private StringBuilder userID = new StringBuilder("test");

    @Before
    public void setUp() {
        File file = new File("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + this.userID + ".ics");
        while (file.exists()) {
            userID.append("t");
            file = new File("src" + File.separator + "main" + File.separator +
                    "resources" + File.separator + this.userID + ".ics");
        }
        this.userFile = new FileManager(userID.toString());
    }

    @After
    public void tearDown() {
        File file = new File("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + this.userID + ".ics");
        file.delete();
    }

    @Test(expected = EmptyCalendarException.class)
    public void testThrowEmptyCalendarException() throws EmptyCalendarException {
        this.userFile.getCalendar();
    }

    @Test
    public void testAddEvent() throws ParseException, IOException {
        AdditionEvent test_event = new AdditionEvent();
        test_event.setName("Test event");
        test_event.setDate("10.10.2010");
        test_event.setTime("15:15");
        test_event.setDescription("Test description");
        java.util.Calendar expectedDate = new GregorianCalendar(2010, java.util.Calendar.OCTOBER, 10, 15, 15, 0);
        Calendar out = this.userFile.addEvent(test_event);

        String description = null;
        String eventName = null;
        Date date = null;
        int count = 0;
        ComponentList events = out.getComponents();
        for (Object elem : events) {
            count++;
            VEvent event = (VEvent) elem;
            description = event.getDescription().getValue();
            eventName = event.getSummary().getValue();
            date = event.getStartDate().getDate();
        }
        assertEquals(1, count);
        assertEquals("Test description", description);
        assertEquals("Test event", eventName);
        assertEquals(expectedDate.getTime().getTime(), date.getTime());
    }
}
