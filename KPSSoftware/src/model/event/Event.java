package model.event;

import java.time.LocalDateTime;

/**
 * This class is the base class of all events
 *
 * @author Hector
 * @version 2017/5/20
 */
public abstract class Event {

    /**
     * The id of this event
     */
    public final int id;

    /**
     * The staffId who logged this event
     */
    private int staffId;

    /**
     * The time stamp of when this event happened
     */
    private LocalDateTime timeStamp;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     */
    public Event(int id, int staffId, LocalDateTime timeStamp) {
        this.id = id;
        this.staffId = staffId;
        this.timeStamp = timeStamp;
    }

    /**
     * @return the id of the staff who logged this event
     */
    public int getStaffId() {
        return this.staffId;
    }

    /**
     * @return the timestamp of this event
     */
    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Event))
            return false;

        Event event = (Event) o;

        return id == event.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
