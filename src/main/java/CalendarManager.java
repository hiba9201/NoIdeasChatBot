import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.filter.Rule;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.bson.Document;

import java.io.*;
import java.util.Comparator;


public class CalendarManager {
    private MongoClient mongoClient;

    private MongoDatabase db;
    private MongoCollection<Document> table;

    public CalendarManager() {
        mongoClient = MongoClients.create();
        db = mongoClient.getDatabase("TMB_DB");
        table = db.getCollection("Schedules");
    }

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
        Document newUser = new Document("_id", id)
                .append("schedule", null);

        table.insertOne(newUser);
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
        String strCalendar = calendarToString(calendar);
        Document schedule = new Document("$set", new Document("schedule", strCalendar));

        FindIterable<Document> user = table.find(Filters.eq("_id", id));

        if (user.first() == null) {
            createUser(id);
        }

        table.updateOne(Filters.eq("_id", id), schedule);
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
        FindIterable<Document> user = table.find(Filters.eq("_id", id));

        if (user.first() == null) {
            createUser(id);

            user = table.find(Filters.eq("_id", id));
        }

        if (user.first().get("schedule") == null) {
            throw new EmptyCalendarException("В календаре отсутствуют события", id.toString());
        }

        StringReader sin = new StringReader(String.valueOf(user.first().get("schedule")));
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
