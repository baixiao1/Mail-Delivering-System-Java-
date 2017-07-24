package model.route;

import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;

import java.util.*;

/**
 * This class manages all routes in this programme. In addition, this class maintains a graph system internally to
 * perform path-finding tasks.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class RoutingSystem {

    /**
     * All nodes in the graph, where each node represents a location. They are maintained as a HashMap, where the key is
     * the id of the location, and the value is the GraphNode.
     */
    private Map<Integer, GraphNode> graphNodes;

    /**
     * All edges in the graph, where each edge represents a route. They are maintained as a HashMap, where the key is
     * the id of the route, and the value is the GraphEdge.
     */
    private Map<Integer, GraphEdge> graphEdges;

    /**
     * Constructor. This constructor accepts a set of routes, and construct the graph system from those routes.
     *
     * @param routes the routes that compose the graph
     */
    public RoutingSystem(Map<Integer, Route> routes) {
        this.graphNodes = new HashMap<>();
        this.graphEdges = new HashMap<>();

        routes.values().forEach(this::addGraphRoute);
    }

    /**
     * Constructor. There is no graph built after constructing. Use it when you want to add routes later.
     */
    public RoutingSystem() {
        this.graphNodes = new HashMap<>();
        this.graphEdges = new HashMap<>();
    }

    /**
     * This method clears all GraphNodes and all GraphEdges in the routing system. Proceed with caution.
     */
    public void clearAll() {
        this.graphNodes.clear();
        this.graphEdges.clear();
    }

    /**
     * Finds a series of continuous routes that can be used for the given mail. If no continuous routes can be found for
     * the given mail, this method will return null.
     *
     * @param mail the mail that we want to find the routes for
     * @return an ordered list of routes,
     */
    public List<Route> findRoutes(Mail mail) {
        NZLocation origin = mail.getOrigin();
        Location destination = mail.getDestination();
        Priority priority = mail.getPriority();
        double weight = mail.getWeight();
        double volume = mail.getVolume();

        if ((destination instanceof InternationalLocation && !priority.isInternational())
                || (destination instanceof NZLocation && priority.isInternational())) {
            // domestic mail should not have international priority, and vice versa.
            return null;
        }

        GraphNode start = graphNodes.get(origin.id);
        GraphNode end = graphNodes.get(destination.id);

        return findPath(start, end, priority, weight, volume);
    }

    /**
     * Add a route into the graph
     *
     * @param route
     * @return true if successful, or false if failed.
     */
    public boolean addGraphRoute(Route route) {
        // do not add same route twice
        if (this.graphEdges.values().stream().anyMatch(graphEdge -> graphEdge.getRoute().id == route.id)) {
            return false;
        }

        // for each location, find the GraphNode; if there is none, create a new one.
        Location start = route.getStartLocation();
        Location end = route.getEndLocation();

        GraphNode startNode = graphNodes.get(start.id);
        if (startNode == null) {
            startNode = new GraphNode(start);
            graphNodes.put(startNode.getLocationId(), startNode);
        }

        GraphNode endNode = graphNodes.get(end.id);
        if (endNode == null) {
            endNode = new GraphNode(end);
            graphNodes.put(endNode.getLocationId(), endNode);
        }

        // for the route, create a GraphEdge
        GraphEdge edge = new GraphEdge(route, startNode, endNode);
        graphEdges.put(route.id, edge);

        // update Dijkstra graph, make sure the edge and two nodes are connected to correct neighbours
        return startNode.addOutgoingEdge(edge);
    }

    /**
     * Delete the route with the given id.
     *
     * @param id the id of the route that needs to be deleted
     * @return true if the deletion is successful, or false if failed.
     */
    public boolean deleteGraphRouteById(int id) {
        // if there is no such route with the given id, return false
        if (this.graphEdges.values().stream().noneMatch(graphEdge -> graphEdge.getRoute().id == id)) {
            return false;
        }

        GraphEdge deletedEdge = graphEdges.remove(id);
        GraphNode startNode = deletedEdge.getStart();

        return startNode.removeOutgoingEdge(deletedEdge);
    }

    /**
     * Use Dijkstra algorithm to find a path from the given start node to the given end node. If no continuous routes
     * can be found, this method will return null.
     *
     * @param start
     * @param end
     * @param priority
     * @param weight
     * @param volume
     * @return
     */
    private List<Route> findPath(GraphNode start, GraphNode end, Priority priority, double weight, double volume) {
        // a collection storing all GraphNode that are visited
        Set<Integer> visitedNodes = new HashSet<>();

        // a priority queue which is necessary for the path-finding algorithm
        PriorityQueue<DijkstraNode> fringe = new PriorityQueue<>();

        // enqueue the start GraphNode
        fringe.offer(new DijkstraNode(start, null, null));

        while (!fringe.isEmpty()) {
            DijkstraNode polledNode = fringe.poll();
            // mark it as visited by adding it into visited set.
            visitedNodes.add(polledNode.getGraphNode().getLocationId());

            // if the end GraphNode is dequeued, the path is found
            if (polledNode.getGraphNode().getLocationId() == end.getLocationId()) {
                List<Route> path = new ArrayList<>();
                DijkstraNode backTracer = polledNode;
                // back trace it from end to start.
                while (backTracer.getCameFromNode() != null) {
                    path.add(backTracer.getEdgeToParent().getRoute());
                    backTracer = backTracer.getCameFromNode();
                }

                Collections.reverse(path);
                return path;
            }

            for (int edgeId : polledNode.getGraphNode().getOutgoingEdgeIds()) {
                GraphEdge edge = graphEdges.get(edgeId);

                // filter out those who don't suit the priority.
                if (!isOKForPriority(edge, priority)) {
                    continue;
                }

                GraphNode endNode = edge.getEnd();

                // filter out those visited
                if (visitedNodes.contains(endNode.getLocationId())) {
                    continue;
                }

                // a flag to mark if this neighbor is updated or added directly
                boolean isInFringe = false;
                DijkstraNode neighbour = new DijkstraNode(endNode, polledNode, edge);

                for (DijkstraNode dijkstraNode : fringe) {
                    if (dijkstraNode.getGraphNode().getLocationId() == endNode.getLocationId()) {
                        // if already in fringe, see if its cost needs to be updated
                        compareAndUpdate(neighbour, dijkstraNode, edge, weight, volume);
                        isInFringe = true;
                        break;
                    }
                }

                // if not in fringe, add it in.
                if (!isInFringe) {
                    fringe.offer(neighbour);
                }
            }
        }

        // if cannot find a path, return null
        return null;
    }

    /**
     * Can we use this edge for the given priority?
     *
     * @param edge
     * @param priority
     * @return
     */
    private boolean isOKForPriority(GraphEdge edge, Priority priority) {
        Route route = edge.getRoute();

        if (Priority.International_Air.equals(priority)) {
            return !route.isInternationalRoute() || RouteType.Air.equals(route.routeType);
        } else if (Priority.Domestic_Air.equals(priority)) {
            return !route.isInternationalRoute() && RouteType.Air.equals(route.routeType);
        }

        return true;
    }

    /**
     * Update the profit so far on Dijkstra node, and update the path from root.
     *
     * @param neighbour
     * @param nodeInFringe
     * @param newEdgeToParent
     * @param weight
     * @param volume
     */
    private void compareAndUpdate(DijkstraNode neighbour, DijkstraNode nodeInFringe, GraphEdge newEdgeToParent, double weight, double volume) {
        if (neighbour.getProfitFromStart() < nodeInFringe.getProfitFromStart()) {
            nodeInFringe.setProfitFromStart(neighbour.getProfitFromStart());
            nodeInFringe.setEdgeToParent(newEdgeToParent);
            nodeInFringe.updateProfitFromStart(weight, volume);
        }
    }
}
