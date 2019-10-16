import net.fortuna.ical4j.model.Date;

public class AdditionEvent {
    private String name;
    private String description;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = new Date();
    }

    public void setTime(String time) {
        // меняет this.date
    }
}
