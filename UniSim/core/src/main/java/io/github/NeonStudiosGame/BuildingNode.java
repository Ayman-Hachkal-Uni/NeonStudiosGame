package io.github.NeonStudiosGame;

import io.github.NeonStudiosGame.buildings.BaseBuilding;
import io.github.NeonStudiosGame.buildings.Road;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuildingNode {
    private BaseBuilding building;
    private List<BuildingNode> connections;
    private boolean traversable;

    public BuildingNode(BaseBuilding building) {
        this.building = building;
        this.connections = new ArrayList<BuildingNode>();
        traversable = checkTraversible(building);
    }

    private boolean checkTraversible(BaseBuilding building) {
        if (building == null || !(building instanceof Road)) {
            return false;
        }
        return true;
    }

    public void setBuilding(BaseBuilding building) {
        this.building = building;
        this.traversable = checkTraversible(building);
    }

    public int[] getCoords() {
        int x = building.getPosition()[1];
        int y = building.getPosition()[0];
        return new int[]{x, y};
    }

    public BaseBuilding getBuilding() {
        return building;
    }

    public List<BuildingNode> getConnections() {
        return List.copyOf(connections);
    }


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
