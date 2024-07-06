package routing;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


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

        if ( start_node == null || end_node == null) {
            
            throw new NoSuchRouteException();
        }
        else {
            return computeRouteLeg(g, start_node, end_node, tt);
        }
        
    }

    @Override
    public RouteLeg computeRouteLeg (Graph g, Node start_node, Node end_node, TravelType tt) throws NoSuchRouteException {

        // Priority queue of the nodes with non-infinity weight, sorted by weight

        PriorityQueue<NodeFactory> priorityQueue = new PriorityQueue<>((n1, n2) -> Double.compare(n1.getWeight() ,n2.getWeight() )); 

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

                for (Edge e : currentNode.edges) 
                {
                    if (e == null) {
                        continue;
                    }

                    if (e.allowsTravelType(tt, Direction.FORWARD))
                    {
                        NodeFactory neighborNode = (NodeFactory) e.getEnd();

                        if (neighborNode.getVisited() == false) 
                        {
                            // Add the neighbor node to the priority queue if it is not already in it
                            if (priorityQueue.contains(neighborNode) == false) 
                            {
                                priorityQueue.add(neighborNode);
                            }

                            // Calculate the distance between the two nodes
                            double length = currentNode.getCoordinate().getDistance(neighborNode.getCoordinate());

                            // Calculate the weight of the neighbor node
                            double newWeight = currentNode.getWeight() + length;

                            if (newWeight < neighborNode.getWeight()) 
                            {
                                // Remove from queue, update weight and add back to queue
                                priorityQueue.remove(neighborNode);
                                neighborNode.setWeight(newWeight);
                                priorityQueue.add(neighborNode);

                                neighborNode.setPrevious(currentNode);
                            }

                            
                        }
                    }
                }

                // Step 2 : Move to the node with the smallest weight
                currentNode = priorityQueue.poll();
                if (currentNode == null) 
                {
                    throw new NoSuchRouteException();
                }
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
