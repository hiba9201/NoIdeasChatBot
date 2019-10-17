import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class TestAdditionEvent {
    private AdditionEvent additionEvent = new AdditionEvent();

    @Test
    public void TestCorrectDate() throws ParseException {
        Calendar c = new GregorianCalendar(2019, 9, 17, 9, 30, 0);
        c.set(Calendar.MILLISECOND, 0);

        additionEvent.setDate("17.10.2019");
        additionEvent.setTime("09:30");
        assertEquals(c.getTime().toString(), additionEvent.getDate().getTime().toString());
    }

    @Test
    public void Test12Hours() throws ParseException {
        Calendar c = new GregorianCalendar(2019, 9, 17, 12, 30, 0);
        c.set(Calendar.MILLISECOND, 0);

        additionEvent.setDate("17.10.2019");
        additionEvent.setTime("12:30");
        assertEquals(c.getTime().toString(), additionEvent.getDate().getTime().toString());
    }
}
