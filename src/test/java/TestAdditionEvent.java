import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class TestAdditionEvent {
    private AdditionEvent additionEvent = new AdditionEvent();

    @Test
    public void testCorrectDate() throws ParseException {
        Calendar c = new GregorianCalendar(2019, 9, 17, 9, 30, 0);
        c.set(Calendar.MILLISECOND, 0);

        additionEvent.setDate("17.10.2019");
        additionEvent.setTime("09:30");
        assertEquals(c.getTime().toString(), additionEvent.getDate().getTime().toString());
    }

    @Test
    public void test12Hours() throws ParseException {
        Calendar c = new GregorianCalendar(2019, 9, 17, 12, 30, 0);
        c.set(Calendar.MILLISECOND, 0);

        additionEvent.setDate("17.10.2019");
        additionEvent.setTime("12:30");
        assertEquals(c.getTime().toString(), additionEvent.getDate().getTime().toString());
    }

    @Test
    public void testMoreThen12Hours() throws ParseException {
        Calendar c = new GregorianCalendar(2019, 9, 17, 19, 30, 0);
        c.set(Calendar.MILLISECOND, 0);

        additionEvent.setDate("17.10.2019");
        additionEvent.setTime("19:30");
        assertEquals(c.getTime().toString(), additionEvent.getDate().getTime().toString());
    }

    @Test
    public void testLessThen12Hours() throws ParseException {
        Calendar c = new GregorianCalendar(2019, 9, 17, 7, 30, 0);
        c.set(Calendar.MILLISECOND, 0);

        additionEvent.setDate("17.10.2019");
        additionEvent.setTime("7:30");
        assertEquals(c.getTime().toString(), additionEvent.getDate().getTime().toString());
    }

    @Test(expected = ParseException.class)
    public void testUnformatDate() throws ParseException {
        additionEvent.setDate("17/10/2019");
    }

    @Test(expected = ParseException.class)
    public void testUnformatTime() throws ParseException {
        additionEvent.setTime("10.10");
    }

//    @Test(expected = IncorrectData.class)
//    public void testIncorrectDate() throws ParseException {
//        additionEvent.setDate("32.10.2019");
//    }

//    @Test(expected = IncorrectData.class)
//    public void testUIncorrectTime() {
//        additionEvent.setTime("25:70");
//    }
}
