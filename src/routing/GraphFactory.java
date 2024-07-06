package routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

    public Coordinate getNECoordinate() {
        
        Coordinate nw = this.getNWCoordinate();
        Coordinate se = this.getSECoordinate();
        return new Coordinate(nw.getLatitude(), se.getLongitude());
    }

    public Coordinate getSWCoordinate() {
        
        Coordinate nw = this.getNWCoordinate();
        Coordinate se = this.getSECoordinate();
        return new Coordinate(se.getLatitude(), nw.getLongitude());
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

        ArrayList<Node> isolatedNodes = new ArrayList<>();
        
        int numNodesRemoved = 0;
        for (Node node : this.nodesGraph.values()) {
            if (node.numEdges() == 0) {
                ++numNodesRemoved;
                isolatedNodes.add(node);
            }
        }

        for (Node node : isolatedNodes) {
            this.nodesGraph.remove(node.getId());
            this.numNodes--;
        }

        return numNodesRemoved;
    }

    @Override
    public int removeUntraversableEdges(RoutingAlgorithm ra, TravelType tt) {

        
        int numEdgesRemoved = 0;
        Direction dir = Direction.FORWARD; // Default direction

        if (ra.isBidirectional()){
            dir = Direction.ANY;
        }

        for (Node node : this.nodesGraph.values()) {

            List<Integer> edgeRmvList = new ArrayList<>();
            int egdeCounterInsideNode = 0;

            for (Edge edge : node) {

                if (!edge.allowsTravelType(tt, dir)) {
                    // node.removeEdge(egdeCounterInsideNode);
                    edgeRmvList.add(egdeCounterInsideNode);
                    ++numEdgesRemoved;
                }

                
                ++egdeCounterInsideNode;

            }

            if (edgeRmvList.size() > 0) {
                for (int i = edgeRmvList.size() - 1; i >= 0; i--) {
                node.removeEdge(edgeRmvList.get(i));
                this.numEdges--;
                }
            }
        }

        return numEdgesRemoved;
        
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

    // public void tooString () {
        
    //     for (Node node : this.nodesGraph.values()) {
    //         System.out.println("Node ID: " + node.getId());
    //         System.out.println("Node Coordinate: " + node.getCoordinate().getLatitude() + ", " + node.getCoordinate().getLongitude());
    //         System.out.println("Number of Edges: " + node.numEdges());
    //         System.out.println("Edges: ");
    //         for (Edge edge : node) {
    //             System.out.println("Edge Length: " + edge.getLength());
    //             System.out.println("Edge Start Node: " + edge.getStart().getId());
    //             System.out.println("Edge End Node: " + edge.getEnd().getId());
    //         }
    //     }
    // }

    public boolean convertToBoolean(int i) {
		if (i == 1) {
			return true;
		} else if (i == 0) {
			return false;
		} else {
			throw new IllegalArgumentException();
		}
	}
    
}