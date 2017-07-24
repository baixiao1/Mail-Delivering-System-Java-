package model.route;

/**
 * This class represents the node used in path finding with dijkstra algorithm
 *
 * @author Hector
 * @version 2017/5/29
 */
public class DijkstraNode implements Comparable<DijkstraNode> {

    /**
     * The GraphNode that this DijkstraNode is representing for
     */
    private GraphNode graphNode;

    /**
     * The DijkstraNode that this Node comes from, i.e. the parent node along the path.
     */
    private DijkstraNode cameFrom;

    /**
     * The Graph edge connecting this node and the parent node
     */
    private GraphEdge edgeToParent;

    /**
     * The total profit from the root node till this node.
     */
    private double profitFromStart = 0;

    /**
     * Constructor
     *
     * @param selfNode
     * @param fromNode
     * @param edgeToParent
     */
    public DijkstraNode(GraphNode selfNode, DijkstraNode fromNode, GraphEdge edgeToParent) {
        this.graphNode = selfNode;
        this.cameFrom = fromNode;
        this.edgeToParent = edgeToParent;
    }

    /**
     * This method updates the total profit from the root node till this node.
     *
     * @param weight the weight used to calculate the mail's revenue
     * @param volume the volume used to calculate the mail's revenue
     */
    public void updateProfitFromStart(double weight, double volume) {
        if (cameFrom == null) {
            this.profitFromStart = 0;
        } else {
            this.profitFromStart = this.edgeToParent.getRouteProfit(weight, volume) + this.cameFrom.getProfitFromStart();
        }
    }

    /**
     * @return the GraphNode that this DijkstraNode is representing for
     */
    public GraphNode getGraphNode() {
        return this.graphNode;
    }

    /**
     * @return the parent node along the path, i.e. which node do I came from?
     */
    public DijkstraNode getCameFromNode() {
        return this.cameFrom;
    }

    /**
     * @return the graph edge connecting this node and the parent node
     */
    public GraphEdge getEdgeToParent() {
        return this.edgeToParent;
    }

    /**
     * This method sets the graph edge connecting this node and the parent node
     *
     * @param edgeToParent
     */
    public void setEdgeToParent(GraphEdge edgeToParent) {
        this.edgeToParent = edgeToParent;
    }

    /**
     * @return The total profit from the root node till this node.
     */
    public double getProfitFromStart() {
        return this.profitFromStart;
    }

    /**
     * This method updates the total profit from the root node till this node.
     *
     * @param newCostFromStart
     */
    public void setProfitFromStart(double newCostFromStart) {
        this.profitFromStart = newCostFromStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DijkstraNode))
            return false;

        DijkstraNode that = (DijkstraNode) o;

        return Double.compare(that.profitFromStart, profitFromStart) == 0
                && graphNode.equals(that.graphNode)
                && (cameFrom != null ? cameFrom.equals(that.cameFrom) : that.cameFrom == null)
                && (edgeToParent != null ? edgeToParent.equals(that.edgeToParent) : that.edgeToParent == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = graphNode.hashCode();
        result = 31 * result + (cameFrom != null ? cameFrom.hashCode() : 0);
        result = 31 * result + (edgeToParent != null ? edgeToParent.hashCode() : 0);
        temp = Double.doubleToLongBits(profitFromStart);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(DijkstraNode other) {
        if (this.profitFromStart < other.profitFromStart) {
            return 1;
        } else if (this.profitFromStart > other.profitFromStart) {
            return -1;
        } else {
            return 0;
        }
    }
}
