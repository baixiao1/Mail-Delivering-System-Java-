package model.event;

import java.time.LocalDateTime;

/**
 * This class represents the event of customer price update
 *
 * @author Hector
 * @version 2017/5/20
 */
public class CustomerPriceUpdateEvent extends Event {

    /**
     * The id of the updated route
     */
    private int routeId;

    /**
     * The price per gram before update
     */
    private double oldPricePerGram;

    /**
     * the price per volume before update
     */
    private double oldPricePerVolume;

    /**
     * the new price per gram after update
     */
    private double newPricePerGram;

    /**
     * the new price per volume after update
     */
    private double newPricePerVolume;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     * @param routeId
     * @param oldPricePerGram
     * @param oldPricePerVolume
     * @param newPricePerGram
     * @param newPricePerVolume
     */
    public CustomerPriceUpdateEvent(int id, int staffId, LocalDateTime timeStamp, int routeId, double oldPricePerGram,
                                    double oldPricePerVolume, double newPricePerGram, double newPricePerVolume) {
        super(id, staffId, timeStamp);
        this.routeId = routeId;
        this.oldPricePerGram = oldPricePerGram;
        this.oldPricePerVolume = oldPricePerVolume;
        this.newPricePerGram = newPricePerGram;
        this.newPricePerVolume = newPricePerVolume;
    }

    /**
     * @return the id of the updated route
     */
    public int getRouteId() {
        return routeId;
    }

    /**
     * @return the old price per gram
     */
    public double getOldPricePerGram() {
        return oldPricePerGram;
    }

    /**
     * @return the old price per volume
     */
    public double getOldPricePerVolume() {
        return oldPricePerVolume;
    }

    /**
     * @return the new price per gram
     */
    public double getNewPricePerGram() {
        return newPricePerGram;
    }

    /**
     * @return the new price per volume
     */
    public double getNewPricePerVolume() {
        return newPricePerVolume;
    }

    @Override
    public String toString() {
        return "CustomerPriceUpdateEvent{" +
                "id=" + id +
                ", routeId=" + routeId +
                ", oldPricePerGram=" + oldPricePerGram +
                ", oldPricePerVolume=" + oldPricePerVolume +
                ", newPricePerGram=" + newPricePerGram +
                ", newPricePerVolume=" + newPricePerVolume +
                "}";
    }
}
