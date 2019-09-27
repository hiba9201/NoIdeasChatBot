public class User {
    private String scheduleFileName;
    private String ID;

    User(String fileName, String ID) {
        this.scheduleFileName = fileName;
        this.ID = ID;
    }

    String getScheduleFileName() {
        return scheduleFileName;
    }

    public String getID() {
        return ID;
    }
}
