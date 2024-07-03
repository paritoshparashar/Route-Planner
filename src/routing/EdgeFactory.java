package routing;


public class EdgeFactory implements Edge {

    public Node startNode;
    public Node destinationNode;
    public double length;

    public boolean isCarAllowedForward;
    public boolean isCarAllowedBackward;
    public boolean isBikeAllowedForward;
    public boolean isBikeAllowedBackward;
    public boolean isFootAllowedForward;
    public boolean isFootAllowedBackward;

    
    public EdgeFactory(Node startNode, Node destinationNode, boolean forwardCarSignal, boolean backwardCarSignal, boolean forwardBikeSignal, boolean backwardBikeSignal, boolean forwardFootSignal, boolean backwardFootSignal) {
        this.startNode = startNode;
        this.destinationNode = destinationNode;
        this.length = 0.0;

        this.isCarAllowedForward = forwardCarSignal;
        this.isCarAllowedBackward = backwardCarSignal;
        this.isBikeAllowedForward = forwardBikeSignal;
        this.isBikeAllowedBackward = backwardBikeSignal;
        this.isFootAllowedForward = forwardFootSignal;
        this.isFootAllowedBackward = backwardFootSignal;

    }
    
    @Override
    public boolean allowsTravelType(TravelType tt, Direction dir) {

        if (dir == Direction.FORWARD) {
           
            if (tt == TravelType.CAR) {
                return this.isCarAllowedForward;
            } else if (tt == TravelType.BIKE) {
                return this.isBikeAllowedForward;
            } else if (tt == TravelType.FOOT) {
                return this.isFootAllowedForward;
            } else {
                return this.isCarAllowedForward || this.isBikeAllowedForward || this.isFootAllowedForward;
            }
        }
        
        else if (dir == Direction.BACKWARD) {
                
                if (tt == TravelType.CAR) {
                    return this.isCarAllowedBackward;
                } else if (tt == TravelType.BIKE) {
                    return this.isBikeAllowedBackward;
                } else if (tt == TravelType.FOOT) {
                    return this.isFootAllowedBackward;
                } else {
                    return this.isCarAllowedBackward || this.isBikeAllowedBackward || this.isFootAllowedBackward;
                }
            }
        else if (dir == Direction.ANY) {
            if (tt == TravelType.CAR) {
                return this.isCarAllowedForward || this.isCarAllowedBackward;
            } else if (tt == TravelType.BIKE) {
                return this.isBikeAllowedForward || this.isBikeAllowedBackward;
            } else if (tt == TravelType.FOOT) {
                return this.isFootAllowedForward || this.isFootAllowedBackward;
            } else {
                return this.isCarAllowedForward || this.isBikeAllowedForward || this.isFootAllowedForward || this.isCarAllowedBackward || this.isBikeAllowedBackward || this.isFootAllowedBackward;
            }
        }
        else {
            return false;
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