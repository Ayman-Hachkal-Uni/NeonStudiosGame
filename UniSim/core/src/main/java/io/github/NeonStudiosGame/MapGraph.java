package io.github.NeonStudiosGame;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.NeonStudiosGame.buildings.BaseBuilding;

import java.util.*;

/**
 * This class is the graph that represents the map in terms of navigation, i.e., which placed buildings connect to
 * each other, this is currently used to calculate score bonuses and debuffs. The class stores BuildingNodes in a 2D
 * array and as a list. It can be used to calculate the distance between two buildings on the map.
 */
public class MapGraph {

    private final List<BuildingNode> nodes;
    private final BuildingNode[][] mapArray;

    /**
     * This is the constructor for the class, it takes in the tile layer we're using to calculate the size of the map
     * array required. It sets the empty array to the given map dimensions and initialises a new arraylist of the nodes.
     * @param layer The tile layer used for objects being placed (The texture layer of the map).
     */
    public MapGraph(TiledMapTileLayer layer) {
        mapArray = new BuildingNode[layer.getWidth()][layer.getHeight()];
        nodes = new ArrayList<>();
    }

    private void addNode(BuildingNode node) {
        nodes.add(node);
    }

    /**
     * Sets the position of the given node in the map array and adds it ot the list of nodes.
     * @param node The node to add.
     * @param x The X coordinate of the cell that the building that the node represents belongs in.
     * @param y The X coordinate of the cell that the building that the node represents belongs in.
     */
    public void addNode(BuildingNode node, int x, int y){
        mapArray[x][y] = node;
        this.addNode(node);
    }

    /**
     * Gets the building node that represents a given building by finding the building node in the same map spot as the
     * building.
     * @param building the building that the node they're looking for represents.
     * @return The node that represents the given building.
     */
    public BuildingNode getNode(BaseBuilding building) {
        int x = building.getPosition()[0];
        int y = building.getPosition()[1];
        return mapArray[x][y];
    }

    /**
     * Gets the node that the building given represents and then assigns the given building to it.
     * @param building The building to assign to its respective node.
     */
    public void updateBuilding(BaseBuilding building) {
        getNode(building).setBuilding(building);
    }


    /**
     * This sets up the connections between all the nodes in the graph, by default they should connect to the nodes
     * directly one cell above, below, left and right of them. When this is run it's assumed that the Building node of
     * the map are already filled in.
     */
    public void initConnections() {
        for (int x = 0; x < mapArray.length; x++) {
            for (int y = 0; y < mapArray[x].length; y++) {
                if (mapArray[x][y] == null) {
                    continue;
                }
                if (y < mapArray[x].length - 1) {
                    mapArray[x][y].addConnection(mapArray[x][y + 1]);
                    mapArray[x][y + 1].addConnection(mapArray[x][y]);
                }
                if (x < mapArray[x].length - 1) {
                    mapArray[x][y].addConnection(mapArray[x + 1][y]);
                    mapArray[x + 1][y].addConnection(mapArray[x][y]);
                }
            }
        }
    }

    /**
     * This method uses dijkstra's algorithm to calculate the shortest path between two nodes in the graph. It acts as
     * the driver for the full algorithm.
     * @param startNode The start node.
     * @param targetNode The target node.
     * @return An integer of the shortest distance found between two nodes.
     */
    public int distanceBetween(BuildingNode startNode, BuildingNode targetNode) {
        // Start by setting this map of distances to "infinite" for all values except for the startNode, which is set to
        // 0
        Map<BuildingNode, Integer> distances = new HashMap<>();
        for (BuildingNode[] buildingNodes : mapArray) {
            for (BuildingNode node : buildingNodes) {
                if (startNode.getConnections().contains(node)) {
                    distances.put(node, 1);
                } else {
                    distances.put(node, Integer.MAX_VALUE);
                }
            }
        }
        distances.put(startNode, 0);
        Set<BuildingNode> unvisited = new HashSet<>(distances.keySet());
        recursiveDijkstras(distances, unvisited, startNode, targetNode);
        return distances.get(targetNode);
    }

    /**
     * This is the recursive part of the algorithm it uses dijkstra's algorithm - for more info visit this section of
     * the wiki article <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm">Dijkstra's algorithm
     * </a> (Accessed 09/11/24). This part of the code uses steps 3-6 (in the order 4,5,6,3).
     * @param distances The map showing the current shortest distance calculated between the root node and the given
     * node in the map.
     * @param unvisited The set of nodes that haven't been checked yet.
     * @param root The current/initial node to search from.
     * @param dest The target node to find the shortest distance to from the start node.
     */
    private void recursiveDijkstras(Map<BuildingNode,Integer> distances,
                                    Set<BuildingNode> unvisited,
                                    BuildingNode root,
                                    BuildingNode dest) {

        if (!unvisited.contains(root)) {
            return;
        }
        if (root != dest) {
            Set<BuildingNode> toVisitNext = new HashSet<>();
            for (BuildingNode neighbour : root.getConnections()) {
                if ((neighbour.isTraversable() || neighbour == dest)){
                    int distance = distances.get(root) + 1;
                    distances.put(neighbour, Integer.min(distance, distances.get(neighbour)));
                    toVisitNext.add(neighbour);
                }
            }
            unvisited.remove(root);
            for (BuildingNode neighbour : toVisitNext) {
                recursiveDijkstras(distances, unvisited, neighbour, dest);
            }
        }
    }
}
