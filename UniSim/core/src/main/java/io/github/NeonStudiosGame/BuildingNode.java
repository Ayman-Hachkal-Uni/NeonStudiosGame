package io.github.NeonStudiosGame;

import io.github.NeonStudiosGame.buildings.BaseBuilding;
import io.github.NeonStudiosGame.buildings.Road;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a Node to make up a graph structure. It stores the connections to other BuildingNodes and the building
 * that it represents, also whether this node can be traversed.
 */
public class BuildingNode {
    private BaseBuilding building;
    private final List<BuildingNode> connections;
    private boolean traversable;

    /**
     * This is the constructor for the class, it sets the building given and initialises the connections as empty, it
     * then uses checkTraversable to set the initial traversability.
     * @param building The building that the node represents in the map graph.
     */
    public BuildingNode(BaseBuilding building) {
        this.building = building;
        this.connections = new ArrayList<>();
        traversable = checkTraversable(building);
    }

    /**
     * Calculates if the building specified is traversable, currently checks if it inherits from road.
     * @param building the building to check the traversability of.
     * @return the boolean stating if the building is traversable.
     */
    public boolean checkTraversable(BaseBuilding building) {
        return building instanceof Road;
    }

    /**
     * Sets the building that the node represents and then recalculates the traversability, in case it changes.
     * @param building The building to assign to this node.
     */
    public void setBuilding(BaseBuilding building) {
        this.building = building;
        this.traversable = checkTraversable(building);
    }

    /**
     * Gets the coordinates of the building that the node represents.
     * @return an array [x,y] representing the coordinates of the cell, that the building the node represents, belongs
     * in.
     */
    public int[] getCoords() {
        if (building == null) {
            return null;
        }
        int x = building.getPosition()[1];
        int y = building.getPosition()[0];
        return new int[]{x, y};
    }

    public BaseBuilding getBuilding() {
        return building;
    }

    /**
     * Allows the user to get/see the connections of this node, via a shallow copy.
     * @return a shallow copy of the list of connections.
     */
    public List<BuildingNode> getConnections() {
        return List.copyOf(connections);
    }

    /**
     * Attempts to add a connection to the list of connections. If the connection already exists then false is returned.
     * @param connection The node to add to connections.
     * @return The boolean representing whether the connection is a new connection to the node/whether it is added.
     */
    public boolean addConnection(BuildingNode connection) {
        if (connections.contains(connection)) {
            return false;
        }
        connections.add(connection);
        return true;
    }

    public boolean isTraversable() {
        return traversable;
    }

    public boolean removeConnection(BuildingNode connection) {
        return connections.remove(connection);
    }
}
