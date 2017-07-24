package model.location;

/**
 * This enum class represents a city in New Zealand.
 *
 * @author Hector
 * @version 2017/5/20
 */
public enum NZCity {
    Auckland, Hamilton, Rotorua, Palmerston_North, Wellington, Christchurch, Dunedin;

    /**
     * Given a string, find the matched NZCity.
     *
     * @param cityName
     * @return The matched NZCity if there is one, or null if there isn't.
     */
    public static NZCity createFromString(String cityName) {
        for (NZCity nzCity : NZCity.values()) {
            if (nzCity.toString().equalsIgnoreCase(cityName)) {
                return nzCity;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
}
