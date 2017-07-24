package model.mail;

/**
 * This Enum class represents several types of priority
 *
 * @author Hector
 * @version 2017/5/20
 */
public enum Priority {
    International_Air, International_Standard, Domestic_Air, Domestic_Standard;

    /**
     * The price factor for international priorities. International priorities are more expensive than domestic
     * priorities.
     */
    private static final double INTERNATIONAL_AIR_FACTOR = 1.25f;

    /**
     * The price factor for international priorities. International priorities are more expensive than domestic
     * priorities.
     */
    private static final double INTERNATIONAL_STANDARD_FACTOR = 1.15f;

    /**
     * The price factor for domestic priorities.
     */
    private static final double DOMESTIC_AIR_FACTOR = 1.1f;

    /**
     * The price factor for international priorities. International priorities are more expensive than domestic
     * priorities.
     */
    private static final double DOMESTIC_STANDARD_FACTOR = 1.0f;

    /**
     * @return the price factor
     */
    public double getPriceFactor() {
        switch (this) {
            case International_Air:
                return INTERNATIONAL_AIR_FACTOR;
            case International_Standard:
                return INTERNATIONAL_STANDARD_FACTOR;
            case Domestic_Air:
                return DOMESTIC_AIR_FACTOR;
            case Domestic_Standard:
                return DOMESTIC_STANDARD_FACTOR;
            default:
                return 0;  // dead code
        }
    }

    /**
     * @return <i>true</i> if it's international priority, or <i>false</i> if it's domestic priority.
     */
    public boolean isInternational() {
        switch (this) {
            case International_Air:
            case International_Standard:
                return true;
            case Domestic_Air:
            case Domestic_Standard:
                return false;
            default:
                return false;  // dead code
        }
    }

    /**
     * @return <i>true</i> if it's air priority, or <i>false</i> if it's standard priority.
     */
    public boolean isAir() {
        switch (this) {
            case International_Air:
            case Domestic_Air:
                return true;
            case International_Standard:
            case Domestic_Standard:
                return false;
            default:
                return false;  // dead code
        }
    }

    /**
     * Given a string, find the matched Priority.
     *
     * @param priorityString
     * @return
     */
    public static Priority createPriorityFrom(String priorityString) {
        for (Priority priority : Priority.values()) {
            if (priority.toString().equals(priorityString)) {
                return priority;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
}
