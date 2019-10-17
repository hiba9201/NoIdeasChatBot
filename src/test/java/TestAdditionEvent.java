import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class TestAdditionEvent {
    private AdditionEvent additionEvent = new AdditionEvent();

    @Test
    public void testParseCorrectDate() throws ParseException {
        Calendar calendar = new GregorianCalendar(2000, Calendar.DECEMBER, 11, 9, 54, 0);
        additionEvent.setDate("11.12.2000");
        additionEvent.setTime("09:54");
        assertEquals(calendar.getTime(), additionEvent.getDate().getTime());
    }

    @Test
    public void testParseCorrectTimeWithSingleCharacterInHours() throws ParseException {
        Calendar calendar = new GregorianCalendar(2019, Calendar.OCTOBER, 17, 5, 5, 0);
        additionEvent.setDate("17.10.2019");
        additionEvent.setTime("5:5");
        assertEquals(calendar.getTime(), additionEvent.getDate().getTime());
    }

    @Test
    public void testParseCorrectDateWithSingleCharacterInDay() throws ParseException {
        Calendar calendar = new GregorianCalendar(2019, Calendar.OCTOBER, 7, 15, 15, 0);
        additionEvent.setDate("7.10.2019");
        additionEvent.setTime("15:15");
        assertEquals(calendar.getTime(), additionEvent.getDate().getTime());
    }

    @Test
    public void testParseCorrectTime12Hours() throws ParseException {
        Calendar calendar = new GregorianCalendar(2030, Calendar.JANUARY, 18, 12, 15, 0);
        additionEvent.setDate("18.01.2030");
        additionEvent.setTime("12:15");
        assertEquals(calendar.getTime(), additionEvent.getDate().getTime());
    }

    @Test
    public void testParseCorrectTimeMoreThen12Hours() throws ParseException {
        Calendar calendar = new GregorianCalendar(2018, Calendar.MAY, 20, 14, 10, 0);
        additionEvent.setDate("20.05.2018");
        additionEvent.setTime("14:10");
        assertEquals(calendar.getTime(), additionEvent.getDate().getTime());
    }

    @Test
    public void testParseCorrectTimeLessThen12Hours() throws ParseException {
        Calendar calendar = new GregorianCalendar(2002, Calendar.FEBRUARY, 27, 7, 30, 0);
        additionEvent.setDate("27.02.2002");
        additionEvent.setTime("07:30");
        assertEquals(calendar.getTime(), additionEvent.getDate().getTime());
    }

    @Test(expected = ParseException.class)
    public void testParseUnformatDate() throws ParseException {
        additionEvent.setDate("17/10/2019");
    }

    @Test(expected = ParseException.class)
    public void testParseUnformatTime() throws ParseException {
        additionEvent.setTime("10.10");
    }

    @Test(expected = ParseException.class)
    public void testParseDateWithIncorrectDay() throws ParseException {
        additionEvent.setDate("32.10.2019");
    }

    @Test(expected = ParseException.class)
    public void testParseDateWithIncorrectMonth() throws ParseException {
        additionEvent.setDate("10.13.2019");
    }

    @Test(expected = ParseException.class)
    public void testParseUIncorrectTime() throws ParseException {
        additionEvent.setTime("25:10");
    }
}
