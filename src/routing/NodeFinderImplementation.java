package routing;

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

        
        double candidateNodeDistance = 0.0;
        
         // Find the cell in the grid that contains the coordinate
            int numRows = this.gridObj.gridRows();
            int numCols = this.gridObj.gridColumns();

            double cellWidth = this.gridObj.cellWidth();
            double cellHeight = this.gridObj.cellHeight();

            int row = this.gridObj.getRow(c);
            int col = this.gridObj.getCol(c);

        // Get the nodes in the cell
            ArrayList<Long> nodesInCell = this.gridObj.getGrid()[row][col];


            if (nodesInCell != null) {
                // Candidate Node is in the same cell
                candidateNodeDistance = this.calculateClosestNodeDistanceInAGridCell(c, nodesInCell);    
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
                            if (row + i >= 0 && row + i < numRows && col + j >= 0 && col + j < numCols) {
                                if (this.gridObj.getGrid()[row + i][col + j] != null) {
                                    nodeListInNeighbouringCell.addAll(this.gridObj.getGrid()[row + i][col + j]);
                                    found = true;
                                    break;
                                }
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
                candidateNodeDistance = this.calculateClosestNodeDistanceInAGridCell(c, nodeListInNeighbouringCell);
            }

            // Now we have the candidate node, we need create the boundary box around the candidate node
                // to calculate the true closest node
                double radius = candidateNodeDistance;
                CoordinateBox boundaryBox = c.computeBoundingBox(candidateNodeDistance);
                

    }

    public double calculateClosestNodeDistanceInAGridCell (Coordinate c, ArrayList<Long> nodesInCell) {

        double minDistanceCandidateNode = Double.MAX_VALUE;

        for (Long nodeId : nodesInCell) {

            Node currentNode = this.graph.getNode(nodeId);
            double distance = currentNode.getCoordinate().getDistance(c);

            if (distance < minDistanceCandidateNode) {
                minDistanceCandidateNode = distance;
            }
        }

        return minDistanceCandidateNode;
    }


    
}
