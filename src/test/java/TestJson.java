import org.junit.Test;
import static org.junit.Assert.*;

public class TestJson {
    @Test
    public void testExampleParse() {
        String jsonString = "[{\"Name\": \"День рождения?)\",\"Date\": \"29.11.2019\",\"Time\": \"00:00\"}]";
        ScheduleEvent res = FileManager.jsonParse(jsonString)[0];
        ScheduleEvent expected = new ScheduleEvent("День рождения?)", "29.11.2019", "00:00");
        assertEquals(expected.Date, res.Date);
        assertEquals(expected.Name, res.Name);
        assertEquals(expected.Time, res.Time);
    }
}
