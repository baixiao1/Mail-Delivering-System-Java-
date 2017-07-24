package model.location;

/**
 * This class is the base class of any location. A location class represents a location, which can be used as the
 * origin or destination of a mail.
 *
 * @author Hector
 * @version 2017/5/20
 */
public abstract class Location {

    /**
     * The id of this location
     */
    public final int id;

    /**
     * Constructor
     *
     * @param locationId
     */
    public Location(int locationId) {
        this.id = locationId;
    }

    /**
     * @return the name of this location
     */
    public abstract String getLocationName();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Location))
            return false;

        Location location = (Location) o;

        return id == location.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
