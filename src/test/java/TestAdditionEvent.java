import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void TestMoreThen12Hours() throws ParseException {
        Calendar c = new GregorianCalendar(2019, 9, 17, 19, 30, 0);
        c.set(Calendar.MILLISECOND, 0);

        additionEvent.setDate("17.10.2019");
        additionEvent.setTime("19:30");
        assertEquals(c.getTime().toString(), additionEvent.getDate().getTime().toString());
    }

    @Test
    public void TestLessThen12Hours() throws ParseException {
        Calendar c = new GregorianCalendar(2019, 9, 17, 7, 30, 0);
        c.set(Calendar.MILLISECOND, 0);

        additionEvent.setDate("17.10.2019");
        additionEvent.setTime("7:30");
        assertEquals(c.getTime().toString(), additionEvent.getDate().getTime().toString());
    }

    @Test
    public void TestUnformatDate() {
        Throwable e = null;
        try {
            additionEvent.setDate("17/10/2019");
        } catch (ParseException ex) {
            e = ex;
        }
        assertTrue(e instanceof ParseException);
    }

    @Test
    public void TestUnformatTime() {
        Throwable e = null;
        try {
            additionEvent.setTime("10.10");
        } catch (ParseException ex) {
            e = ex;
        }
        assertTrue(e instanceof ParseException);
    }

    @Test
    public void TestIncorrectDate() throws ParseException {
        Throwable e = null;
        try {
            additionEvent.setDate("32.10.2019");
        } catch (ParseException ex) {
            e = ex;
        }
        assertTrue(e instanceof ParseException);
    }

    @Test
    public void TestUIncorrectTime() {
        Throwable e = null;
        try {
            additionEvent.setTime("25:10");
        } catch (ParseException ex) {
            e = ex;
        }
        assertTrue(e instanceof ParseException);
    }
}
