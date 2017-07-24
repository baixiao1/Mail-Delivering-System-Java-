package model.event;

import java.time.LocalDateTime;

/**
 * This class represents an event of transport cost update
 *
 * @author Hector
 * @version 2017/5/20
 */
public class TransportCostUpdateEvent extends Event {

    /**
     * The id of the updated route
     */
    private int routeId;

    /**
     * The cost per gram before update
     */
    private double oldCostPerGram;

    /**
     * the cost per volume before update
     */
    private double oldCostPerVolume;

    /**
     * the new cost per gram after update
     */
    private double newCostPerGram;

    /**
     * the new cost per volume after update
     */
    private double newCostPerVolume;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     * @param routeId
     * @param oldCostPerGram
     * @param oldCostPerVolume
     * @param newCostPerGram
     * @param newCostPerVolume
     */
    public TransportCostUpdateEvent(int id, int staffId, LocalDateTime timeStamp, int routeId, double oldCostPerGram,
                                    double oldCostPerVolume, double newCostPerGram, double newCostPerVolume) {
        super(id, staffId, timeStamp);
        this.routeId = routeId;
        this.oldCostPerGram = oldCostPerGram;
        this.oldCostPerVolume = oldCostPerVolume;
        this.newCostPerGram = newCostPerGram;
        this.newCostPerVolume = newCostPerVolume;
    }

    /**
     * @return the id of the updated route
     */
    public int getRouteId() {
        return routeId;
    }

    /**
     * @return the old cost per gram
     */
    public double getOldCostPerGram() {
        return oldCostPerGram;
    }

    /**
     * @return the old cost per volume
     */
    public double getOldCostPerVolume() {
        return oldCostPerVolume;
    }

    /**
     * @return the new cost per gram
     */
    public double getNewCostPerGram() {
        return newCostPerGram;
    }

    /**
     * @return the new cost per volume
     */
    public double getNewCostPerVolume() {
        return newCostPerVolume;
    }

    @Override
    public String toString() {
        return "TransportCostUpdateEvent{" +
                "id=" + id +
                ", routeId=" + routeId +
                ", oldCostPerGram=" + oldCostPerGram +
                ", oldCostPerVolume=" + oldCostPerVolume +
                ", newCostPerGram=" + newCostPerGram +
                ", newCostPerVolume=" + newCostPerVolume +
                "}";
    }
}
