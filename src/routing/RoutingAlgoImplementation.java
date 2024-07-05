package routing;

import java.util.ArrayList;
import java.util.List;

public class RoutingAlgoImplementation implements RoutingAlgorithm{
    
     

    @Override
    public Route computeRoute(Graph g, List<Node> nodes, TravelType tt) throws NoSuchRouteException {
        
        // Initialize the route
        ArrayList<RouteLeg> routeParts = new ArrayList<RouteLeg>();
        double distance = 0.0;
        
        // Compute the different route legs
        for (int i = 0; i < nodes.size() - 1; i++) 
        {
            RouteLeg routeLeg = computeRouteLeg(g, nodes.get(i), nodes.get(i + 1), tt);
            routeParts.add(routeLeg);
            distance += routeLeg.getDistance();
        }

        // Create the route
        RouteImplementation route = new RouteImplementation(distance, nodes.get(0), nodes.get(nodes.size() - 1), routeParts, tt);

        return route;
    }

    @Override
    public RouteLeg computeRouteLeg (Graph g, long startId, long endId, TravelType tt) throws NoSuchRouteException {
        
        Node start_node = g.getNode(startId);
        Node end_node = g.getNode(endId);
        return computeRouteLeg(g, start_node, end_node, tt);
    }

    @Override
    public RouteLeg computeRouteLeg (Graph g, Node start_node, Node end_node, TravelType tt) throws NoSuchRouteException {

        NodeFactory startN = (NodeFactory) start_node;
        NodeFactory endN = (NodeFactory) end_node;

        // Initialize the start node
            startN.setWeight(0.0);
            startN.setPrevious(null);

        // Start the algorithm
        NodeFactory currentNode = startN;
        currentNode.setVisited(true);

            while (endN.getVisited() == false) 
            {
                /*
                 * Step 1 : Calculate the distance between the current node and all its "unvisited" neighbors
                 * * while doing so, keep track of the smallest weight
                */

                double smallestEdgeLength = Double.MAX_VALUE;
                NodeFactory smallestNode = null;

                for (Edge e : currentNode.edges) 
                {
                    if (e.allowsTravelType(tt, Direction.FORWARD))
                    {
                        NodeFactory neighborNode = (NodeFactory) e.getEnd();

                        if (neighborNode.getVisited() == false) 
                        {

                            // Calculate the distance between the two nodes
                            double length = currentNode.getCoordinate().getDistance(neighborNode.getCoordinate());

                            if (length < smallestEdgeLength) 
                            {
                                smallestEdgeLength = length;
                                smallestNode = neighborNode;
                            }

                            // Calculate the weight of the neighbor node
                            double newWeight = currentNode.getWeight() + length;

                            if (newWeight < neighborNode.getWeight()) 
                            {
                                neighborNode.setWeight(newWeight);
                                neighborNode.setPrevious(currentNode);
                            }

                        }
                    }
                }

                // Step 2 : Move to the node with the smallest weight
                currentNode = smallestNode;
                currentNode.setVisited(true);
                
            }

            // Step 3 : Reconstruct the path
            ArrayList<Node> nodesRoute = new ArrayList<Node>();

            while (currentNode != null) 
            {
                nodesRoute.add(currentNode);
                currentNode = (NodeFactory) currentNode.getPrevious();
            }
            
            // reverse the list
            ArrayList<Node> nodesRouteReversed = new ArrayList<Node>();
            for (int i = nodesRoute.size() - 1; i >= 0; i--) 
            {
                nodesRouteReversed.add(nodesRoute.get(i));
            }

            // Step 4 : Create the RouteLeg
            RouteLegImplementation routeLeg = new RouteLegImplementation(endN.getWeight(), startN, endN, nodesRouteReversed);

            return routeLeg;
    }

    @Override
    public boolean isBidirectional() {
        // TODO Auto-generated method stub
        return false;
    }

}
