package routing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class Factory {

	/**
	 * Create a graph from the description in a .nae file.
	 *
	 * @param fileName
	 *            A path to an NAE file.
	 *
	 * @return The graph as described in the .nae file.
	 *
	 * @throws IOException
	 *             If an Input/Output error occurs.
	 */
	public static Graph createGraphFromMap(String fileName) throws IOException {
		
		GraphFactory graph = new GraphFactory();
		
		try (BufferedReader br = new BufferedReader(new FileReader (fileName))){

			String line;

			while ( (line = br.readLine()) != null ) {

				String [] lineParts = line.split(" ");

				if (lineParts[0].equals("N")) { // Node Read
					long iD = Long.parseLong(lineParts[1]);
					double lat = Double.parseDouble(lineParts[2]);
					double lon = Double.parseDouble(lineParts[3]);

					NodeFactory createdNode = new NodeFactory(iD, lat, lon);
					graph.nodesGraph.put(iD, createdNode);
					graph.numNodes++;

				}
				else if (lineParts[0].equals("E")) { // Edge Read
					
					long startNodeId = Long.parseLong(lineParts[1]);
					long endNodeId = Long.parseLong(lineParts[2]);
					boolean forwardCar = Boolean.parseBoolean(lineParts[3]);
					boolean backwardCar = Boolean.parseBoolean(lineParts[4]);
					boolean forwardBike = Boolean.parseBoolean(lineParts[5]);
					boolean backwardBike = Boolean.parseBoolean(lineParts[6]);
					boolean forwardFoot = Boolean.parseBoolean(lineParts[7]);
					boolean backwardFoot = Boolean.parseBoolean(lineParts[8]);

					Node originNode = graph.getNode(startNodeId);
					Node destinationNode = graph.getNode(endNodeId);

					if (originNode != null && destinationNode != null) {
						EdgeFactory forwardEdge = new EdgeFactory(originNode, destinationNode, forwardCar, forwardBike, forwardFoot, Direction.FORWARD);
						originNode.addEdge(forwardEdge);
						// graph.numEdges++; // Do i have to increment the number of edges here too?

						EdgeFactory backwardEdge = new EdgeFactory(destinationNode, originNode, backwardCar, backwardBike, backwardFoot, Direction.BACKWARD);
						destinationNode.addEdge(backwardEdge);
						graph.numEdges++;
					}
					else {
						throw new IOException();
						
					}

				}
				else {
					throw new IOException();
				}

			}
			
			return graph;
		} catch (IOException e) {
			throw new IOException();
		}
	}

	/**
	 * Return a node finder algorithm for the graph g. The graph argument allows
	 * the node finder to build internal data structures.
	 *
	 * @param g
	 *            The graph the nodes are looked up in.
	 * @return A node finder algorithm for that graph.
	 */
	public static NodeFinder createNodeFinder(Graph g) {
		// TODO: Implement me.
		return null;
	}

	/**
	 * == BONUS ==
	 *
	 * Compute the overlay graph (or junction graph).
	 *
	 * Note: This is part of a bonus exercise, not of the regular project.
	 *
	 * @return The overlay graph for the given graph g.
	 */
	public static Graph createOverlayGraph(Graph g) {
		// TODO: Implement me.
		return null;
	}

	/**
	 * Return a routing algorithm for the graph g. This allows to inspect the
	 * graph and choose from different routing strategies if appropriate.
	 *
	 * @param g
	 *            The graph the routing is performed on.
	 * @return A routing algorithm suitable for that graph.
	 */
	public static RoutingAlgorithm createRoutingAlgorithm(Graph g) {
		// TODO: Implement me.
		return null;
	}

}
