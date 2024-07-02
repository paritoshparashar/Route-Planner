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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coordinate getSECoordinate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<Node> iterator() {
        // TODO Auto-generated method stub
        return null;
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