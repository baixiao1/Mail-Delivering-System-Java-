package model.route;

import model.location.InternationalLocation;
import model.location.Location;

/**
 * This class represents a postal route between two locations.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class Route {

    /**
     * The id of this route
     */
    public final int id;

    /**
     * The type of this route
     */
    public final RouteType routeType;

    /**
     * The start location of this route
     */
    private final Location start;

    /**
     * The end location of this route
     */
    private final Location end;

    /**
     * The duration that this route takes to go from start to end
     */
    private final double duration;

    /**
     * The company operating on thie route
     */
    private final String transportFirm;

    /**
     * The price per gram
     */
    private double pricePerGram;

    /**
     * The price per volume
     */
    private double pricePerVolume;

    /**
     * The cost per gram
     */
    private double costPerGram;

    /**
     * The cost per volume
     */
    private double costPerVolume;

    /**
     * Is this route active?
     */
    private boolean isActive;

    /**
     * Constructor
     *
     * @param id
     * @param start
     * @param end
     * @param routeType
     * @param duration
     * @param transportFirm
     * @param pricePerGram
     * @param pricePerVolume
     * @param costPerGram
     * @param costPerVolume
     */
    public Route(int id, Location start, Location end, RouteType routeType, double duration, String transportFirm,
                 double pricePerGram, double pricePerVolume, double costPerGram, double costPerVolume) throws IllegalArgumentException {
        if (start.id == end.id) {
            throw new IllegalArgumentException("Invalid route: two ends cannot be the same");
        }

        this.id = id;
        this.start = start;
        this.end = end;
        this.routeType = routeType;
        this.duration = duration;
        this.transportFirm = transportFirm;
        this.pricePerGram = pricePerGram;
        this.pricePerVolume = pricePerVolume;
        this.costPerGram = costPerGram;
        this.costPerVolume = costPerVolume;
        this.isActive = true;
    }

    /**
     * @return the start location
     */
    public Location getStartLocation() {
        return start;
    }

    /**
     * @return the end location
     */
    public Location getEndLocation() {
        return end;
    }

    /**
     * @return the price per gram
     */
    public double getPricePerGram() {
        return pricePerGram;
    }

    /**
     * Set the price per gram
     *
     * @param pricePerGram
     */
    public void setPricePerGram(double pricePerGram) {
        this.pricePerGram = pricePerGram;
    }

    /**
     * @return the price per volume
     */
    public double getPricePerVolume() {
        return pricePerVolume;
    }

    /**
     * Set the price per volume
     *
     * @param pricePerVolume
     */
    public void setPricePerVolume(double pricePerVolume) {
        this.pricePerVolume = pricePerVolume;
    }

    /**
     * @return the cost per gram
     */
    public double getCostPerGram() {
        return costPerGram;
    }

    /**
     * Set the cost per gram
     *
     * @param costPerGram
     */
    public void setCostPerGram(double costPerGram) {
        this.costPerGram = costPerGram;
    }

    /**
     * @return the cost per volume
     */
    public double getCostPerVolume() {
        return costPerVolume;
    }

    /**
     * Set the cost per volume
     *
     * @param costPerVolume
     */
    public void setCostPerVolume(double costPerVolume) {
        this.costPerVolume = costPerVolume;
    }

    /**
     * Calculate the revenue of this route.
     *
     * @param weight the weight used to calculate the mail's revenue
     * @param volume the volume used to calculate the mail's revenue
     * @return the revenue of this route.
     */
    public double getRevenue(double weight, double volume) {
        return pricePerGram * weight + pricePerVolume * volume;
    }

    /**
     * Calculate the expenditure(cost) of this route.
     *
     * @param weight the weight used to calculate the mail's expenditure(cost)
     * @param volume the volume used to calculate the mail's expenditure(cost)
     * @return the expenditure(cost) of this route.
     */
    public double getExpenditure(double weight, double volume) {
        return costPerGram * weight + costPerVolume * volume;
    }

    /**
     * Calculate the profit of this route.
     *
     * @param weight the weight used to calculate the mail's profit
     * @param volume the volume used to calculate the mail's profit
     * @return the profit of this route.
     */
    public double getNetProfit(double weight, double volume) {
        return getRevenue(weight, volume) - getExpenditure(weight, volume);
    }

    /**
     * @return the duration that this route takes to go from start to end
     */
    public double getDuration() {
        return duration;
    }

    /**
     * @return the company who run this route.
     */
    public String getTransportFirm() {
        return transportFirm;
    }

    /**
     * @return true if this route is international route, or false if it's domestic
     */
    public boolean isInternationalRoute() {
        return start instanceof InternationalLocation || end instanceof InternationalLocation;
    }

    /**
     * @return is this route active?
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Set the route as active or inactive. An inactive route cannot be used to deliver mails.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the string representation for displaying the route's information
     */
    public String getRouteDescription() {
        return "{Origin: " + start.getLocationName() + " Destination: " + end.getLocationName() + " Type: " + routeType.name()
                + " Duration: " + String.format("%.2f", duration) + " Transport Firm: " + transportFirm + " Price Per Gram: "
                + String.format("%.2f", pricePerGram) + " Price Per Volume: " + String.format("%.2f", pricePerVolume)
                + " Cost Per Gram: " + String.format("%.2f", costPerGram) + " Cost Per Volume: "
                + String.format("%.2f", costPerVolume) + " Status: " + isActive + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Route))
            return false;

        Route route = (Route) o;

        return id == route.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", routeType=" + routeType +
                ", start=" + start +
                ", end=" + end +
                ", duration=" + duration +
                ", transportFirm='" + transportFirm + '\'' +
                ", pricePerGram=" + pricePerGram +
                ", pricePerVolume=" + pricePerVolume +
                ", costPerGram=" + costPerGram +
                ", costPerVolume=" + costPerVolume +
                ", isActive=" + isActive +
                '}';
    }
}
