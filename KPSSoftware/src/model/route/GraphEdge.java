package model.route;

/**
 * This class represents an edge between two nodes in the graph data structure.
 *
 * @author Hector
 * @version 2017/5/29
 */
public class GraphEdge {

    /**
     * The route that this GraphEdge is representing for, i.e. the route connecting two locations.
     */
    private final Route route;

    /**
     * The start node of this edge
     */
    private GraphNode start;

    /**
     * The end node of this edge
     */
    private GraphNode end;

    /**
     * Constructor
     */
    public GraphEdge(Route route, GraphNode start, GraphNode end) {
        this.route = route;
        this.start = start;
        this.end = end;
    }

    /**
     * @return the start node of this edge
     */
    public GraphNode getStart() {
        return start;
    }

    /**
     * @return the end node of this edge
     */
    public GraphNode getEnd() {
        return end;
    }

    /**
     * @return the route that this GraphEdge is representing for, i.e. the route connecting two locations.
     */
    public Route getRoute() {
        return this.route;
    }

    /**
     * Retrieve the profit of this route.
     *
     * @param weight the weight used to calculate the mail's profit
     * @param volume the volume used to calculate the mail's profit
     * @return the profit of the route.
     */
    public double getRouteProfit(double weight, double volume) {
        return this.route.getNetProfit(weight, volume);
    }
}
