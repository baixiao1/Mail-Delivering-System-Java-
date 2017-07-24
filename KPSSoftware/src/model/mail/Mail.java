package model.mail;

import model.location.Location;
import model.location.NZLocation;
import model.route.Route;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents a mail
 *
 * @author Hector
 * @version 2017/5/20
 */
public class Mail {

    /**
     * The id of this mail
     */
    public final int id;

    /**
     * The origin of this mail, i.e. where does this mail start
     */
    private NZLocation origin;

    /**
     * the destination of this mail, i.e. where does this mail end
     */
    private Location destination;

    /**
     * The weight of this mail
     */
    private double weight;

    /**
     * The volume of this mail
     */
    private double volume;

    /**
     * The priority of this mail.
     */
    private Priority priority;

    /**
     * The date when the mail is delivered
     */
    private LocalDate deliveryDate;

    /**
     * A series of routes that connects origin to destination.
     * <p>
     * IMPORTANT: Mail object itself is not responsible for checking the validity of the routes. Make sure it's valid
     * before passing the routes in here.
     */
    private List<Route> routes;

    /**
     * Constructor
     *
     * @param id
     * @param origin
     * @param destination
     * @param weight
     * @param volume
     * @param priority
     */
    public Mail(int id, NZLocation origin, Location destination, double weight, double volume, Priority priority, LocalDate deliveryDate) {
        if (origin.id == destination.id) {
            throw new IllegalArgumentException("Invalid mail: origin and destination cannot be the same");
        }

        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.volume = volume;
        this.priority = priority;
        this.deliveryDate = deliveryDate;
    }

    /**
     * @return the origin of this mail
     */
    public NZLocation getOrigin() {
        return this.origin;
    }

    /**
     * @return the destination of this mail
     */
    public Location getDestination() {
        return this.destination;
    }

    /**
     * @return the weight of this mail
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return the volume of this mail
     */
    public double getVolume() {
        return volume;
    }

    /**
     * @return the priority of this mail
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @return teh date when this mail gets delivered
     */
    public LocalDate getDeliveryDate() {
        return this.deliveryDate;
    }

    /**
     * @return the series of routes that connects origin to destination.
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * This method assigns a series of routes to this mail. These routes should be connecting origin
     * to destination.
     *
     * @param routes a series of routes that connects origin to destination. IMPORTANT: this method or any method in
     *               Mail class is not responsible for checking the validity of these these routes. Make sure they are
     *               valid before calling setRoutes().
     */
    public void setRoutes(List<Route> routes) {
        if (routes == null || routes.isEmpty()) {
            throw new IllegalArgumentException("Invalid mail: parameter 'routes' is illegal");
        }

        this.routes = routes;
    }

    /**
     * @return the total revenue of this mail
     */
    public double getRevenue() {
        if (routes == null || routes.isEmpty()) {
            throw new UnsupportedOperationException("This mail has no valid routes, please set the routes before calculating");
        }

        // go through the routes assigned to this mail, calculate the revenue of each route, and add them together.
        return routes.stream()
                .mapToDouble(route -> route.getRevenue(weight, volume))
                .reduce(0, (sum, revenue) -> sum += revenue)
                * priority.getPriceFactor();
    }

    /**
     * @return the total expenditure(cost) of this mail
     */
    public double getExpenditure() {
        if (routes == null || routes.isEmpty()) {
            throw new UnsupportedOperationException("This mail has no valid routes, please set the routes before calculating");
        }

        // go through the routes assigned to this mail, calculate the cost of each route, and add them together.
        return routes.stream()
                .mapToDouble(route -> route.getExpenditure(weight, volume))
                .reduce(0, (sum, expenditure) -> sum += expenditure);
    }

    /**
     * @return the total duration of this mail.
     */
    public double getDuration() {
        if (routes == null || routes.isEmpty()) {
            throw new UnsupportedOperationException("This mail has no valid routes, please set the routes before calculating");
        }

        // go through the routes assigned to this mail, calculate the duration of each route, and add them together.
        return routes.stream()
                .mapToDouble(Route::getDuration)
                .reduce(0, (sum, duration) -> sum += duration);
    }

    /**
     * @return a string representation used for critical view.
     */
    public String toStringForCriticalView() {
        double loss = getExpenditure() - getRevenue();
        return "[Origin: " + origin.getLocationName()
                + ", Destination: " + destination.getLocationName()
                + ", Priority: " + priority
                + "]: Mail ID: " + id
                + ", we lost: " + String.format("%.2f", loss) + " dollars.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Mail))
            return false;

        Mail mail = (Mail) o;

        return id == mail.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", origin=" + origin +
                ", destination=" + destination +
                ", weight=" + weight +
                ", volume=" + volume +
                ", priority=" + priority +
                ", deliveryDate=" + deliveryDate +
                ", routes=" + routes +
                '}';
    }
}
