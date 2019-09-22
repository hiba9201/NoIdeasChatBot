public class ScheduleEvent
{
    public String Name;
    public String Date;
    public String Time;

    @Override
    public String toString() {
        return String.format("%0$-30s Дата: %1s %2s", this.Name, this.Date, this.Time);
    }

    public ScheduleEvent(String name, String date, String time)
    {
        this.Name = name;
        this.Date = date;
        this.Time = time;
    }
}
