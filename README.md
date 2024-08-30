# Route Planner

## Overview
In this project, you will implement a route planner in Java. Given a means of transportation, the route planner shall calculate the shortest route between specified coordinates. Moreover, your route planner will also support intermediate coordinates which the route must visit.

Along with some maps, we will provide you with the front end depicted above, as well as the components needed to be able to communicate with the front end. The front end was developed as a prototype in the software engineering lecture from 2012, and although it has been successfully used by other lectures at the university as a debugging and visualization tool, it may not necessarily be free of minor bugs.

The navigation graph, which is encoded as a NAE file, uses Open Street Map (OSM) identifiers (IDs) to identify the nodes. To visualize a given node on a map, you can use one of the many OSM web interfaces. As an example, the node with the ID 539518216 is visualized [here](http://www.openstreetmap.org/node/539518216).

Maps necessary for the project are not part of your git repository, but must be downloaded from the dCMS first. Once downloaded, extract the archived files into the root directory of your repository. To familiarize yourself with the project, we recommend you start by reading the JavaDoc comments in the provided `.java` files inside the routing package, as they are part of the project specification and complement this document.

Section 2 showcases the assignments that guide you towards a route planner implementation. Optional tasks aimed at speeding up your implementation are described in section 3. The appendix gives more technical information, documents the NAE file format, the maps we provide, and the browser frontend you may use for visualization.


