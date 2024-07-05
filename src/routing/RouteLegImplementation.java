package routing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RouteLegImplementation extends RouteLegBase{
    
    private double distance;
    private Node startNode;
    private Node endNode;
    private ArrayList<Node> nodes;
    
    public RouteLegImplementation(double distance, Node startNode, Node endNode, ArrayList<Node> nodes) {
        this.distance = distance;
        this.startNode = startNode;
        this.endNode = endNode;
        this.nodes = nodes;
    }
    
    @Override
    public double getDistance() {
        return this.distance;
    }

    @Override
    public Node getEndNode() {
        return this.endNode;
    }

    @Override
    public Node getStartNode() {
        return this.startNode;
    }
    
    @Override
    public Iterator<Node> iterator() {  
        return this.nodes.iterator();
    }

    @Override
    public int size() {
        return this.nodes.size();
    }
}
