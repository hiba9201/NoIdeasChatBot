public class EmptyCalendarException extends Exception {
    public EmptyCalendarException(String message, String userID) {
        super(message + "\n" + userID);
    }
}