package model;

import java.util.Calendar;
import java.util.Date;


// Represents a basketball league system event.
// Code influenced from: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;


    // EFFECTS: Creates an event with the given description and the current date/time stamp with event description.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }


    // EFFECTS: overrides the equals method
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    // EFFECTS: overrides hashcode
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: overrides toString method
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }

    // getters

    public Date getDate() {
        return dateLogged;
    }


    public String getDescription() {
        return description;
    }
}
