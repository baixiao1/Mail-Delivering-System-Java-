package model.event;

import java.time.LocalDateTime;

/**
 * This class represents an event of route deactivation (transport discontinued)
 *
 * @author Hector
 * @version 2017/5/20
 */
public class RouteDeactivationEvent extends Event {

    /**
     * The id of deleted route
     */
    private int routeId;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     * @param routeId
     */
    public RouteDeactivationEvent(int id, int staffId, LocalDateTime timeStamp, int routeId) {
        super(id, staffId, timeStamp);
        this.routeId = routeId;
    }

    /**
     * @return the id of the deactivated route
     */
    public int getRouteId() {
        return routeId;
    }

    @Override
    public String toString() {
        return "RouteDeactivationEvent{" +
                "id=" + id +
                ", routeId=" + routeId +
                "}";
    }
}
