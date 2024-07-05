package routing;

import java.util.ArrayList;
import java.util.Iterator;

public class RouteImplementation extends RouteBase{
    

    private double distance;
    private Node startNode;
    private Node endNode;
    private ArrayList<RouteLeg> routeParts;
    private TravelType travelType;
    
    public RouteImplementation(double distance, Node startNode, Node endNode, ArrayList<RouteLeg> routeParts, TravelType travelType) {
        this.distance = distance;
        this.startNode = startNode;
        this.endNode = endNode;
        this.routeParts = routeParts;
        this.travelType = travelType;
    }
    
    @Override
    public double distance() {
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
    public TravelType getTravelType() {
        return this.travelType;
    }
    
    @Override
    public Iterator<RouteLeg> iterator() {  
        return this.routeParts.iterator();
    }

    @Override
    public int size() {
        return this.routeParts.size();
    }
}
