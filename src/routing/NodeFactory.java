package routing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NodeFactory implements Node{

    public long id;
    public double lat;
    public double lon;
    private double weight;
    private Node previous;
    private boolean visited;

    public List<Edge> edges;

    public NodeFactory(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;

        this.weight = Double.MAX_VALUE;
        this.previous = null;
        this.visited = false;

        this.edges = new ArrayList<Edge>();
    }
    
    public void setWeight(double w) {
        this.weight = w;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setPrevious(Node n) {
        this.previous = n;
    }

    public Node getPrevious() {
        return this.previous;
    }

    public void setVisited(boolean v) {
        this.visited = v;
    }
    
    public boolean getVisited() {
        return this.visited;
    }
    
    @Override
    public Coordinate getCoordinate() {
        return new Coordinate(this.lat, this.lon);
    }

    @Override
    public Edge getEdge(int idx) {
        return this.edges.get(idx);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public Iterator<Edge> iterator() {
        return this.edges.iterator();
    }

    @Override
    public int numEdges() {
        return this.edges.size();
    }

    @Override
    public void addEdge(Edge e) {
        this.edges.add(e);   
    }

    @Override
    public void removeEdge(int i) {
        this.edges.remove(i);
    }
}
