package model.route;

import model.location.Location;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a node in the graph data structure.
 *
 * @author Hector
 * @version 2017/5/29
 */
public class GraphNode {

    /**
     * The location that this GraphNode is representing for.
     */
    private final Location location;

    /**
     * The set of the ids all edges that goes out from this node, i.e. all the edges that use this location as origin.
     */
    private final Set<Integer> outgoingEdgeIds;

    /**
     * Constructor.
     */
    public GraphNode(Location location) {
        this.location = location;
        outgoingEdgeIds = new HashSet<>();
    }

    /**
     * @return the id of the location
     */
    public int getLocationId() {
        return this.location.id;
    }

    /**
     * @return the set of the ids all edges that goes out from this node, i.e. all the edges that use this location as
     * origin.
     */
    public Set<Integer> getOutgoingEdgeIds() {
        return this.outgoingEdgeIds;
    }

    /**
     * Adds an outgoing edge to this Node
     *
     * @param edge an edge that is going out from this node
     * @return true if the addition is successful, or false if failed or the edge's origin is not this node.
     */
    public boolean addOutgoingEdge(GraphEdge edge) {
        return this.getLocationId() == edge.getStart().getLocationId() && this.outgoingEdgeIds.add(edge.getRoute().id);
    }

    /**
     * Removes an outgoing edge from this Node
     *
     * @param edge an edge that is going out from this node
     * @return true if the deletion is successful, or false if failed or the edge's origin is not this node.
     */
    public boolean removeOutgoingEdge(GraphEdge edge) {
        int id = edge.getRoute().id;
        return this.outgoingEdgeIds.contains(id) && this.outgoingEdgeIds.remove(id);
    }
}
