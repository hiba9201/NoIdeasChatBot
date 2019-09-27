public class User {
    private String scheduleFileName;
    private String ID;

    public User(String fileName, String ID) {
        this.scheduleFileName = fileName;
        this.ID = ID;
    }

    public String getScheduleFileName() {
        return scheduleFileName;
    }

    public String getID() {
        return ID;
    }
}
