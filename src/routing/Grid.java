package routing;

import java.util.ArrayList;

public class Grid {
    
    private ArrayList<Long>[][] grid;
    private GraphFactory graph;



    public Grid(Graph graph) {
        this.graph = (GraphFactory)graph;
        this.grid = this.setGrid();
    }

    public ArrayList<Long>[][] getGrid() {
        return this.grid;
    }

    public int gridRows () {
        return this.grid.length;
    }

    public int gridColumns () {
        return this.grid[0].length;
    }

    public double cellWidth () {
        return (this.graph.getNWCoordinate().getLongitude() - this.graph.getNECoordinate().getLongitude()) 
                            / this.gridColumns(); // (maxLong - minLong) / numCols
    }

    public double cellHeight () {
        return (this.graph.getNWCoordinate().getLatitude() - this.graph.getSWCoordinate().getLatitude()) 
                            / this.gridRows(); // (maxLat - minLat) / numRows
    }

    public int getRow (Coordinate c) {
        return (int) ((c.getLatitude() - this.graph.getSWCoordinate().getLatitude() )/ this.cellHeight());
    }

    public int getCol (Coordinate c) {
        return (int) ((c.getLongitude() - this.graph.getNECoordinate().getLongitude() )/ this.cellWidth());
    }

    public ArrayList<Long>[][] setGrid(){

        // Form a grid over the map
        
        // Step 2 -> get the number of rows and columns of the grid based on number of nodes
        int numNodes = this.graph.numNodes();
        int thresholdForNodesInEachCell = 100;
        int numCells = (numNodes / thresholdForNodesInEachCell) + 1;
        int numRows = (int) Math.sqrt(numCells) + 1;
        int numCols = numRows;

        // Step 3 -> get the width and height of each cell
        double cellWidth =  (this.graph.getNWCoordinate().getLongitude() - this.graph.getNECoordinate().getLongitude()) / numCols;
        double cellHeight = (this.graph.getNWCoordinate().getLatitude() - this.graph.getSWCoordinate().getLatitude()) / numRows;
        
        // Step 4 -> populate the grid with the nodes
        ArrayList<Long>[][] grid = new ArrayList[numRows][numCols];

        for (Node node : graph) {
            Coordinate currentNodeCoordinate = node.getCoordinate();

            int row = (int) ((currentNodeCoordinate.getLatitude() - this.graph.getSWCoordinate().getLatitude()) / cellHeight); // row of the grid to place the node
            int col = (int) ((currentNodeCoordinate.getLongitude() - this.graph.getNECoordinate().getLongitude()) / cellWidth); // column of the grid to place the node
            
            // Safety check that the row and column are within the grid
            row = Math.max(0, Math.min(row, numRows - 1));
            col = Math.max(0, Math.min(col, numCols - 1));

            if (grid[row][col] == null) {
                // Create a new array list and add the node id to it
                grid[row][col] = new ArrayList<Long>();
                grid[row][col].add(node.getId());
            }
            else {
                // Add the node id to the existing array list
                grid[row][col].add(node.getId());
            }
        }

        return grid;

    }

}
