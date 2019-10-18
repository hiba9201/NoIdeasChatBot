import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AdditionEvent {
    private String name;
    private String description;
    private Calendar date = new GregorianCalendar();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        sdf.setLenient(false);
        calendar.setTime(sdf.parse(date));
        this.date.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        this.date.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        this.date.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
    }

    public void setTime(String time) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        sdf.setLenient(false);
        calendar.setTime(sdf.parse(time));
        this.date.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        this.date.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        this.date.set(Calendar.SECOND, 0);
        this.date.set(Calendar.MILLISECOND, 0);
    }
}
