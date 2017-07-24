package model.route;

/**
 * This enum class represents the type of a route.
 *
 * @author Hector
 * @version 2017/5/20
 */
public enum RouteType {
    Air, Land, Sea;

    /**
     * Given a string, find the matched RouteType.
     *
     * @param typeName
     * @return
     */
    public static RouteType createFromString(String typeName) {
        for (RouteType routeType : RouteType.values()) {
            if (routeType.toString().equals(typeName)) {
                return routeType;
            }
        }

        return null;
    }
}
