import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtStart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.GregorianCalendar;


public class TestFileManager {
    private CalendarManager userCalendar;
    private long userID = 11111111;

    @Before
    public void setUp() {
        this.userCalendar = new CalendarManager();
    }

    @Test(expected = EmptyCalendarException.class)
    public void testThrowEmptyCalendarException() throws EmptyCalendarException {
        this.userCalendar.getCalendar(this.userID);
    }

    @Test
    public void testAddEvent() throws ParseException, IOException {
        AdditionEvent test_event = new AdditionEvent();
        test_event.setName("Test event");
        test_event.setDate("10.10.2010");
        test_event.setTime("15:15");
        test_event.setDescription("Test description");
        java.util.Date d = test_event.getDate().getTime();
        java.util.Calendar expectedDate = new GregorianCalendar(2010, java.util.Calendar.OCTOBER,
                10, 14, 15, 0);
        this.userCalendar.addEvent(test_event, this.userID);
        Calendar out = null;
        try {
            out = this.userCalendar.getCalendar(this.userID);
        } catch (EmptyCalendarException e) {
            e.printStackTrace();
        }

        String description = null;
        String eventName = null;
        DtStart date = null;
        int count = 0;
        ComponentList events = out.getComponents();
        for (Object elem : events) {
            count++;
            VEvent event = (VEvent) elem;
            description = event.getDescription().getValue();
            eventName = event.getSummary().getValue();
            date = event.getStartDate();
        }
        Date ddate = date.getDate();
        assertEquals(1, count);
        assertEquals("Test description", description);
        assertEquals("Test event", eventName);
        assertEquals(expectedDate.getTime().getTime(), ddate.getTime());
    }
}
