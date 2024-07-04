package routing;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class NodeFinderImplementation implements NodeFinder {

    // Properties
    private Graph graph;
    // A grid to store the nodes, each cell in the grid will store multiple node(ids)
    private Grid gridObj;

    // Constructor
    public NodeFinderImplementation(Graph graph) {
        this.graph = graph;
        this.gridObj = new Grid(graph);
    }
    
    @Override
    public Node getNodeForCoordinates(Coordinate c) {

        long trueClosestNodeId = 0; 
        double trueClosestNodeDistance = Double.MAX_VALUE;
        double candidateNodeDistance = 0.0;


         // Find the cell in the grid that contains the coordinate
            int numRows = this.gridObj.gridRows();
            int numCols = this.gridObj.gridColumns();

            int row = this.gridObj.getRow(c);
            int col = this.gridObj.getCol(c);

        // Get the nodes in the cell
            ArrayList<Long> nodesInCell = this.gridObj.getGrid()[row][col];


            if (nodesInCell != null) {
                // Candidate Node is in the same cell
                long candidateNodeId = this.calculateClosestNodeInAGridCell(c, nodesInCell);  
                candidateNodeDistance = this.graph.getNode(candidateNodeId).getCoordinate().getDistance(c);   
            }
            else {
                // Get the nodes in the neighbouring cells
                ArrayList<Long> nodeListInNeighbouringCell = new ArrayList<>();

                int searchAreaLat = 1;
                int searchAreaLong = 1;
                boolean found = false;

                while (!found) {

                    for (int i = -(searchAreaLat); i <= (searchAreaLat); i++) {
                        for (int j = -(searchAreaLong); j <= (searchAreaLong); j++) {

                            if ((row + i >= 0 && row + i < numRows && col + j >= 0 && col + j < numCols)
                                    && this.gridObj.getGrid()[row + i][col + j] != null) {
                                

                                    assertTrue(nodeListInNeighbouringCell.addAll(this.gridObj.getGrid()[row + i][col + j]));
                                    found = true;
                                    break;
                                
                            }
                        }
                        if (found) {
                            break;
                        }
                    }
                    // Increase the search area
                    searchAreaLat++;
                    searchAreaLong++;

                }
                    
                
                // Candidate Node is in the neighbouring cell [row+i, col+j]
                long candidateNodeId = this.calculateClosestNodeInAGridCell(c, nodeListInNeighbouringCell);
                candidateNodeDistance = this.graph.getNode(candidateNodeId).getCoordinate().getDistance(c);
            }

            /*
             * Now we have the candidate node, we need to create the boundary box around the candidate node
             * to calculate the true closest node
            */
            
                double radius = candidateNodeDistance;
                CoordinateBox boundaryBox = c.computeBoundingBox(radius);

                // Get the bounds of grid cells that intersect with the boundary box
                Coordinate topLeft = new Coordinate(boundaryBox.getUpperBound().getLatitude() , boundaryBox.getUpperBound().getLongitude());
                Coordinate bottomRight = new Coordinate(boundaryBox.getLowerBound().getLatitude() , boundaryBox.getLowerBound().getLongitude());

                int topLeftRow = this.gridObj.getRow(topLeft);
                int topLeftCol = this.gridObj.getCol(topLeft);

                int bottomRightRow = this.gridObj.getRow(bottomRight);
                int bottomRightCol = this.gridObj.getCol(bottomRight);


                // Get the closest node, by comparing the closest node of each cell in the boundary box
                ArrayList<Long> nodesInBoundaryCells = new ArrayList<>();


                for (int i = topLeftRow; i >= bottomRightRow; i--) {
                    for (int j = topLeftCol; j >= bottomRightCol; j--) {

                        double currentMinDistance = 0.0;

                        if ((i >= 0 && i < numRows && j >= 0 && j < numCols)
                                    && (this.gridObj.getGrid()[i][j] != null)) {
                            
                                assertTrue(nodesInBoundaryCells.addAll(this.gridObj.getGrid()[i][j]));

                                long closestNodeId = calculateClosestNodeInAGridCell(c, nodesInBoundaryCells);
                                currentMinDistance = this.graph.getNode(closestNodeId).getCoordinate().getDistance(c);

                                if (currentMinDistance < trueClosestNodeDistance) {
                                    trueClosestNodeId = closestNodeId;
                                    trueClosestNodeDistance = currentMinDistance;
                                }

                        }

                    }
                }

                // Now we have the true closest node
                return this.graph.getNode(trueClosestNodeId);


    }

    public long calculateClosestNodeInAGridCell (Coordinate c, ArrayList<Long> nodesInCell) {

        double minDistanceCandidateNode = Double.MAX_VALUE;
        Node closestNode = null;

        for (long nodeId : nodesInCell) {

            Node currentNode = this.graph.getNode(nodeId);
            double distance = currentNode.getCoordinate().getDistance(c);

            if (distance < minDistanceCandidateNode) {
                closestNode = currentNode;
                minDistanceCandidateNode = distance;
            }
        }

        return closestNode.getId();
    }


    
}
