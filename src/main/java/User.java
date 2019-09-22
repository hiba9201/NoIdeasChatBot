public class User {
    private String ScheduleFile;

    public User(String fileName) {
        this.ScheduleFile = fileName;
    }

    public String getScheduleFile() {
        return ScheduleFile;
    }

    private void setScheduleFile(String scheduleFile) {
        ScheduleFile = scheduleFile;
    }
}
