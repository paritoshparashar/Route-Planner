package routing;


public class EdgeFactory implements Edge {

    public Node startNode;
    public Node destinationNode;
    public double length;

    public boolean isCarAllowed;
    public boolean isBikeAllowed;
    public boolean isFootAllowed;

    public Direction dir;
    
    public EdgeFactory(Node startNode, Node destinationNode, double length, boolean carSignal, boolean bikeSignal, boolean FootSignal, Direction dir) {
        this.startNode = startNode;
        this.destinationNode = destinationNode;
        this.length = length;

        this.isCarAllowed = carSignal;
        this.isBikeAllowed = bikeSignal;
        this.isFootAllowed = FootSignal;

        this.dir = dir;
    }
    
    @Override
    public boolean allowsTravelType(TravelType tt, Direction dir) {
        if (dir != this.dir) {
            return false; 
        }
        else {
            
            if (tt == TravelType.CAR) {
                return this.isCarAllowed;
            } else if (tt == TravelType.BIKE) {
                return this.isBikeAllowed;
            } else if (tt == TravelType.FOOT) {
                return this.isFootAllowed;
            } else {
                return false;
            }
        }

    }

    @Override
    public Node getEnd() {
        return this.destinationNode;
    }

    @Override
    public double getLength() {
        return this.length;
    }

    @Override
    public Node getStart() {
        return this.startNode;
    }
}