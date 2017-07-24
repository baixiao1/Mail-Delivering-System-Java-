package model.location;

/**
 * This class represents a location in New Zealand. Any location used as the origin of mails must be a NZLocation.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class NZLocation extends Location {

    /**
     * The name of this New Zealand location
     */
    private final NZCity cityName;

    /**
     * Constructor
     *
     * @param locationId
     * @param cityName
     */
    public NZLocation(int locationId, NZCity cityName) {
        super(locationId);
        this.cityName = cityName;
    }

    @Override
    public String getLocationName() {
        return cityName.toString();
    }

    @Override
    public String toString() {
        return "NZLocation{" +
                "id=" + id +
                ", cityName=" + cityName +
                "}";
    }
}
