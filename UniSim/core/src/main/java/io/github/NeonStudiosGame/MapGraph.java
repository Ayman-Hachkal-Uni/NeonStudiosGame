package io.github.NeonStudiosGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.compression.lzma.Base;
import io.github.NeonStudiosGame.buildings.BaseBuilding;
import io.github.NeonStudiosGame.screens.GameScreen;

import java.util.*;

public class MapGraph {

    private List<BuildingNode> nodes;
    private BuildingNode[][] mapArray;

    public MapGraph(TiledMapTileLayer layer) {
        mapArray = new BuildingNode[layer.getWidth()][layer.getHeight()];
        nodes = new ArrayList<BuildingNode>();
    }

    public void addNode(BuildingNode node) {
        nodes.add(node);
    }

    public void addNode(BuildingNode node, int x, int y){
        mapArray[x][y] = node;
        this.addNode(node);
    }

    public BuildingNode getNode(BaseBuilding building) {
        int x = building.getPosition()[0];
        int y = building.getPosition()[1];
        return mapArray[x][y];
    }

    public void updateBuilding(BaseBuilding building) {
        getNode(building).setBuilding(building);
    }

    public void initConnections() {
        try {
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
        catch (Exception e) {e.printStackTrace();}
    }

    public int distanceBetween(BuildingNode a, BuildingNode b) {
        Map<BuildingNode, Integer> distances = new HashMap<BuildingNode, Integer>();
        for (int x = 0; x < mapArray.length; x++) {
            for (BuildingNode node : mapArray[x]) {
                if (a.getConnections().contains(node)) {
                    distances.put(node, 1);

                }
                else {
                    distances.put(node, Integer.MAX_VALUE);
                }
            }
        }
        distances.put(a, 0);
        Set<BuildingNode> unvisited = new HashSet<>();
        unvisited.addAll(distances.keySet());
        recursiveDijkstras(distances, unvisited, a, b);
        return Integer.valueOf(distances.get(b));
    }

    private void recursiveDijkstras(Map<BuildingNode, Integer> distances, Set<BuildingNode> unvisited, BuildingNode root, BuildingNode dest) {
        if (!unvisited.contains(root)) {
            return;
        }
        if (root != dest) {
            Set<BuildingNode> toVisitNext = new HashSet<>();

            for (BuildingNode neighbour : root.getConnections()) {

                if ((neighbour.isTraversable() || neighbour == dest) && unvisited.contains(neighbour)) {

                    Integer distance = distances.get(root) + 1;
                    distances.put(neighbour, Integer.max(distance, distances.get(dest) + 1));
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
