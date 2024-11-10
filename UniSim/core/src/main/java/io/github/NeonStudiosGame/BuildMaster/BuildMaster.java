package io.github.NeonStudiosGame.BuildMaster;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.NeonStudiosGame.BuildingNode;
import io.github.NeonStudiosGame.MapGraph;
import io.github.NeonStudiosGame.Scorer;
import io.github.NeonStudiosGame.screens.GameScreen;
import io.github.NeonStudiosGame.timer.BuildTask;
import io.github.NeonStudiosGame.timer.BuildingScoreTask;
import io.github.NeonStudiosGame.timer.Task;
import io.github.NeonStudiosGame.timer.Timer;
import io.github.NeonStudiosGame.buildings.*;

import java.util.*;


public class BuildMaster {
    private final int debuffDistance = 2;
    private final float debuffPotency = 0.5f;
    private final int baseBoost = 5;
    public Timer timer;
    private BaseBuilding[][] mapArray;
    private final List<BaseBuilding> buildings;
    private GameScreen gameScreen;
    private Scorer scorer;
    private MapGraph graph;
    private int hallsCounter;
    private int barsCounter;
    private int lecturesCounter;
    private int restaurantsCounter;
    private int sportsCount;
    private int busStopsCounter;

    public BuildMaster(GameScreen gameScreen) {
        buildings = new LinkedList<>();
        this.gameScreen = gameScreen;
        this.hallsCounter = 0;
        this.barsCounter = 0;
        this.lecturesCounter = 0;
        this.restaurantsCounter = 0;
        this.sportsCount = 0;
        this.busStopsCounter = 0;
    }

    public boolean setScorer(Scorer scorer) {
        if (this.scorer == null) {
            this.scorer = scorer;
            return true;
        }
        return false;
    }

    public boolean setTimer(Timer timer) {
        if (this.timer == null) {
            this.timer = timer;
            return true;
        }
        return false;
    }

    private BaseBuilding getBuilding(int id, int x, int y) {
        return switch (id) {
            case 2 -> new Lake(new int[] {x,y});
            case 7 -> new Halls(new int[] {x,y});
            case 8 -> new Bar(new int[] {x,y});
            case 9 -> new LectureTheatre(new int[] {x,y});
            case 10 -> new Restaurant(new int[] {x,y});
            case 11 -> new Road(new int[] {x,y});
            case 12 -> new SportsHall(new int[] {x,y});
            case 13 -> new BusStop(new int[] {x,y});
            case 14 -> new Tree(new int[] {x,y});

            default -> null;
        };
    }

    public void setMapArray(TiledMapTileLayer layer) {
        graph = new MapGraph(layer);
        mapArray = new BaseBuilding[layer.getWidth()][layer.getHeight()];
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                BuildingNode buildingNode = new BuildingNode(null);
                graph.addNode(buildingNode, x, y);
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) {
                    mapArray[x][y] = null;
                    continue;
                }
                BaseBuilding building = getBuilding(cell.getTile().getId(), x, y);
                mapArray[x][y] = building;
                buildingNode.setBuilding(building);

            }
        }
        graph.initConnections();
    }

    public void print(Object anything) {
        System.out.println(anything);
    }

    public boolean createBuilding(int[] position, BuildingEnum selection) {
        BaseBuilding building = switch (selection) {
            case BASE_BUILDING -> new BaseBuilding(position);
            case HALLS -> new Halls(position);
            case BAR -> new Bar(position);
            case LECTURE_THEATRE -> new LectureTheatre(position);
            case RESTAURANT -> new Restaurant(position);
            case ROAD -> new Road(position);
            case SPORTS_HALL -> new SportsHall(position);
            case BUS_STOP -> new BusStop(position);
        };

        if (mapArray[position[0]][position[1]] != null) {
            return false;
        }

        switch (selection) {
            case HALLS -> hallsCounter++;
            case BAR -> barsCounter++;
            case LECTURE_THEATRE -> lecturesCounter++;
            case RESTAURANT -> restaurantsCounter++;
            case SPORTS_HALL -> sportsCount++;
            case BUS_STOP -> busStopsCounter++;
        }

        Task buildingCreation = new BuildTask(building.getTimeToBuild() + timer.getGameTime(), building, gameScreen, this);
        timer.scheduleTask(buildingCreation);
        mapArray[position[0]][position[1]] = building;
        return true;
    }

    public void completeConstruction(BaseBuilding building) {
        buildings.add(building);
        gameScreen.renderFullyCompletedBuilding(building);
        graph.updateBuilding(building);

        BuildingNode node = graph.getNode(building);
        if (building instanceof BusStop) {
            for (BaseBuilding otherBuilding : buildings) {
                if (otherBuilding != building && otherBuilding instanceof BusStop) {
                    BuildingNode otherNode = graph.getNode(otherBuilding);
                    node.addConnection(otherNode);
                    otherNode.addConnection(node);
                }
            }
        }

// Gets Dist between all buildings from the constructed one and prints it
//        for (BaseBuilding other : buildings) {
//            System.out.println("DISTANCE between "+building.getClass().getSimpleName() + " and " + other.getClass().getSimpleName() + " is: " + graph.distanceBetween(graph.getNode(building), graph.getNode(other)));
//        }

        if (building instanceof BoosterBuilding) {
            for (BaseBuilding otherBuilding : buildings) {
                if (otherBuilding != building && !(otherBuilding instanceof Road)) {
                    calcModifier(otherBuilding);
                }
            }
        }
        else if (!(building instanceof Road)) {
            calcModifier(building);
        }
        //SET UP SCORE RECURRING TASK
        if (!(building instanceof  Road || building instanceof  Tree || building instanceof  Lake)) {
            BuildingScoreTask scoreTask = new BuildingScoreTask(timer.getGameTime(), building.getScore(), scorer, timer, building.getScoreFrequency(), building);
            timer.scheduleTask(scoreTask);
        }
    }

    private void calcModifier(BaseBuilding building) {
        building.resetModifier();
        addClosenessDebuff(building);
        addBooster(building);
    }

    private void addClosenessDebuff(BaseBuilding building) {
        BuildingNode buildingNode = graph.getNode(building);
        for (BaseBuilding otherBuilding : buildings) {
            if (otherBuilding != building &&
                !(otherBuilding instanceof Road) &&
                graph.distanceBetween(buildingNode, graph.getNode(otherBuilding)) <= debuffDistance) {
                building.multModifier(debuffPotency);
                otherBuilding.multModifier(debuffPotency);
            }
        }

    }

    private void addBooster(BaseBuilding building) {
        BuildingNode buildingNode = graph.getNode(building);
        BoosterBuilding closestBooster = null;
        int closestDist = Integer.MAX_VALUE;
        for (BaseBuilding otherBuilding : buildings) {
            if (building != otherBuilding && otherBuilding instanceof BoosterBuilding) {
                int dist = graph.distanceBetween(buildingNode, graph.getNode(otherBuilding));
                if (dist < closestDist) {
                    closestDist = dist;
                    closestBooster = (BoosterBuilding) otherBuilding;
                }
            }
        }
        if (closestBooster != null && closestDist < Integer.MAX_VALUE) {
            building.multModifier(Math.max(1 + (float) baseBoost / closestDist, 1.1f));
        }
    }

    public List<Integer> getCounter() {
        List<Integer> arrayList = Arrays.asList(hallsCounter, barsCounter, lecturesCounter, restaurantsCounter, sportsCount, busStopsCounter);
        return arrayList;
    }
}
