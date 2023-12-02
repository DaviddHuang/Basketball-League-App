package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of alarm system events using the singleton pattern design
// Code influenced from: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    private EventLog() {
        events = new ArrayList<Event>();
    }


    // MODIFIES: this
    // EFFECTS: gets instance of EventLog, creates it if it doesn't already exist and returns instance of EventLog
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }


    // MODIFIES: this
    // EFFECTS: adds an even to an event log
    public void logEvent(Event e) {
        events.add(e);
    }


    // EFFECTS: clears the event log
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns an iterator over events
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
