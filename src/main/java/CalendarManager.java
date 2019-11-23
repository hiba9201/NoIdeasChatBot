import com.mongodb.*;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.filter.Rule;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import java.io.*;
import java.util.Comparator;
import java.net.UnknownHostException;


public class CalendarManager {
    private MongoClient mongoClient;

    {
        try {
            mongoClient = new MongoClient(new MongoClientURI(System.getenv("MONGODB_URI")));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private DB db = mongoClient.getDB("heroku_wrfvc3pn");
    private DBCollection table = db.getCollection("Schedules");

    private Comparator<VEvent> comparator = new Comparator<VEvent>() {
        @Override
        public int compare(VEvent firstEvent, VEvent secondEvent) {
            Date firstDate = firstEvent.getStartDate().getDate();
            Date secondDate = secondEvent.getStartDate().getDate();
            return firstDate.compareTo(secondDate);
        }
    };


    public void addEvent(AdditionEvent additionEvent, Long id) {
        Calendar calendar;
        try {
            calendar = this.getCalendar(id);
        } catch (EmptyCalendarException e) {
            calendar = this.createCalender();
        }
        assert calendar != null;

        VEvent event = createVEvent(additionEvent, id);
        calendar.getComponents().add(event);
        this.saveCalendar(calendar, id);

    }

    private void createUser(Long id) {
        DBObject newUser = new BasicDBObject("_id", id)
                .append("schedule", null);

        table.insert(newUser);
    }

    private VEvent createVEvent(AdditionEvent additionEvent, Long id) {
        String eventName = additionEvent.getName();
        DateTime start = new DateTime(additionEvent.getDate().getTime());
        VEvent event = new VEvent(start, eventName);

        event.getProperties().add(new Description(additionEvent.getDescription()));
        event.getProperties().add(new Uid(id.toString()));

        return event;
    }

    private void saveCalendar(Calendar calendar, Long id) {
        DBObject query = new BasicDBObject("_id", id);
        DBObject user = table.findOne(query);

        if (user == null) {
            createUser(id);
            user = table.findOne(query);
        }

        String strCalendar = calendarToString(calendar);

        DBObject schedule = new BasicDBObject("schedule", strCalendar);

        table.update(user, schedule);
    }

    private String calendarToString(Calendar calendar) {
        CalendarOutputter outputter = new CalendarOutputter();
        StringWriter sw = new StringWriter();
        try {
            outputter.output(calendar, sw);
        } catch (ValidationException | IOException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }

    private Calendar createCalender() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//timeManagementChatBot"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        return calendar;
    }

    public Calendar getCalendar(Long id) throws EmptyCalendarException {
        DBObject query = new BasicDBObject("_id", id);
        DBObject user = table.findOne(query);

        if (user == null) {
            DBObject newUser = new BasicDBObject("_id", id)
                    .append("schedule", null);
            table.insert(newUser);

            user = table.findOne(query);
        }

        if (user.get("schedule") == null) {
            throw new EmptyCalendarException("В календаре отсутствуют события", id.toString());
        }

        StringReader sin = new StringReader(String.valueOf(user.get("schedule")));
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = null;

        try {
            calendar = builder.build(sin);
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }

        assert calendar != null;
        return calendar;
    }

    public ComponentList getCalendarEvents(Long id, Period period) throws EmptyCalendarException {
        Calendar calendar = this.getCalendar(id);
        Filter filter = new Filter(new Rule[0], Filter.MATCH_ALL);

        if (period != null) {
            filter = new Filter(new Rule[]{new PeriodRule(period)}, Filter.MATCH_ALL);
        }

        ComponentList filtered = (ComponentList) filter.filter(calendar.getComponents());
        filtered.sort(comparator);
        return filtered;
    }
}
