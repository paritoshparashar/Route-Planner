package routing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NodeFactory implements Node{

    public long id;
    public double lat;
    public double lon;
    public List<Edge> edges;

    public NodeFactory(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.edges = new ArrayList<Edge>();
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
