import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;

import java.util.*;

public class Periods {
    public static Period getPeriod(TimeInterval interval) {
        if (interval == TimeInterval.ALL) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        Map<TimeInterval, Dur> duration = new HashMap<>();
        duration.put(TimeInterval.DAY, new Dur(0, 24, 0, 0));
        duration.put(TimeInterval.WEEK, new Dur(7, 0, 0, 0));
        duration.put(TimeInterval.MONTH,
                new Dur(calendar.getMaximum(Calendar.DAY_OF_MONTH), 0, 0, 0));

        for (int field : new int[]{Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}) {
            calendar.set(field, 0);
        }

        if (interval == TimeInterval.DAY) {
            return new Period(new DateTime(calendar.getTime()), duration.get(TimeInterval.DAY));
        }

        if (interval == TimeInterval.WEEK) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            return new Period(new DateTime(calendar.getTime()), duration.get(TimeInterval.WEEK));
        }

        if (interval == TimeInterval.MONTH) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            return new Period(new DateTime(calendar.getTime()), duration.get(TimeInterval.MONTH));
        }

        return null;
    }
}
