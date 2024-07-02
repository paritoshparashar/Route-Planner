package routing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GraphFactory implements Graph {

    public int numNodes;
    public int numEdges;
    public Map<Long, Node> nodesGraph;

    public GraphFactory () {
        this.numNodes = 0;
        this.numEdges = 0;
        this.nodesGraph = new HashMap<>();
    }

    @Override
    public Node getNode(long id) {
        
        if (this.nodesGraph.containsKey(id)) {
            return this.nodesGraph.get(id);
        }
        else {
            return null;
        }
    }

    @Override
    public Coordinate getNWCoordinate() {
        
        double maxLat = Double.MIN_VALUE;
        double maxLong = Double.MIN_VALUE;

        for (Node node : this.nodesGraph.values()) {
            if (node.getCoordinate().getLatitude() > maxLat) {
                maxLat = node.getCoordinate().getLatitude();
            }
            if (node.getCoordinate().getLongitude() > maxLong) {
                maxLong = node.getCoordinate().getLongitude();
            }
        }

        return new Coordinate(maxLat, maxLong);
    }

    @Override
    public Coordinate getSECoordinate() {
         
        double minLat = Double.MAX_VALUE;
        double minLong = Double.MAX_VALUE;

        for (Node node : this.nodesGraph.values()) {
            if (node.getCoordinate().getLatitude() < minLat) {
                minLat = node.getCoordinate().getLatitude();
            }
            if (node.getCoordinate().getLongitude() < minLong) {
                minLong = node.getCoordinate().getLongitude();
            }
        }

        return new Coordinate(minLat, minLong);
    }

    @Override
    public Iterator<Node> iterator() {
        return this.nodesGraph.values().iterator();
    }

    @Override
    public int numEdges() {
        return this.numEdges;
    }

    @Override
    public int numNodes() {
        return this.numNodes;
    }

    @Override
    public int removeIsolatedNodes() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int removeUntraversableEdges(RoutingAlgorithm ra, TravelType tt) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isOverlayGraph() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOverlayGraph'");
    }

    @Override
    public Node getNodeInUnderlyingGraph(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNodeInUnderlyingGraph'");
    }
    
}