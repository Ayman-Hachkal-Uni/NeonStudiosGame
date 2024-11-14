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


/**
 * This class serves as the backend for the buildings and map. It interfaces with the GameScreen and Hud to receive
 * requests and to send updates for them. It also interfaces with the scorer to handle score updates and the timer for
 * scheduling these score updates and building constructions.
 */
@SuppressWarnings("rawtypes")
public class BuildMaster {
    private final int debuffDistance = 2;
    private final float debuffPotency = 0.5f;
    public Timer timer;
    private BaseBuilding[][] mapArray;
    private final List<BaseBuilding> buildings;
    private final GameScreen gameScreen;
    private Scorer scorer;
    private MapGraph graph;
    private int currentlyBuilding;
    private final int maxBuildsAtOnce = 2;
    private final Map<Class, Integer> counters;
    private final Map<Class, Integer> maxPerBuildingType;

    /**
     * The constructor for the build master. This sets an empty LinkedList for the buildings List, sets the game screen
     * to the one specified, sets the amount of buildings currently being built to zero, initalises the
     * maxPerBuildingType map to the numbers hard-coded here, and initialises the counters to 0;
      * @param gameScreen This specifies the gameScreen that the BuildMaster will interface with to update the UI.
     */
    public BuildMaster(GameScreen gameScreen) {
        buildings = new LinkedList<>();
        this.gameScreen = gameScreen;
        this.currentlyBuilding = 0;
        this.maxPerBuildingType = new HashMap<>();
        this.maxPerBuildingType.put(Bar.class, 4);
        this.maxPerBuildingType.put(BusStop.class, 6);
        this.maxPerBuildingType.put(Halls.class, 10);
        this.maxPerBuildingType.put(LectureTheatre.class, 8);
        this.maxPerBuildingType.put(Restaurant.class, 4);
        this.maxPerBuildingType.put(SportsHall.class, 4);
        this.maxPerBuildingType.put(Road.class, 60);
        this.counters = new HashMap<>(Map.copyOf(maxPerBuildingType));
        this.counters.replaceAll((k,v)->0);
    }

    /**
     * This is used to set the scorer to be used by the BuildMaster, it can only be set once.
     * @param scorer The scorer to assign to the BuildMaster.
     * @return a boolean representing whether the scorer was set.
     */
    public boolean setScorer(Scorer scorer) {
        if (this.scorer == null) {
            this.scorer = scorer;
            return true;
        }
        return false;
    }

    /**
     * This is used to set the timer to be used by the BuildMaster, it can only be set once.
     * @param timer The timer to assign to the BuildMaster.
     * @return a boolean representing whether the timer was set.
     */
    public boolean setTimer(Timer timer) {
        if (this.timer == null) {
            this.timer = timer;
            return true;
        }
        return false;
    }

    /**
     * Used locally by this class to create a building of a given id (representing subclass) and position
     * @param id the id that represents the subclass of the building object to create.
     * @param x the x coordinate of the cell that the building should be placed in.
     * @param y the y coordinate of the cell that the building should be placed in.
     * @return returns the new building object requested, or null if the id doesn't match anything.
     */
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

    /**
     * This is used to initialise the 2D array that stores the buildings relative to the map as well as the graph that
     * represents the map and how it's connected.
     * @param layer This is the tile layer to base the initial state of the map on.
     */
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

    /**
     * This creates a building with a given selection, it then checks if there is already a building in this spot or
     * whether the build limits are reached, and then returns whether the building is kept as a boolean. It also
     * schedules the building's final construction with the timer.
     * @param position The array [x,y] specifying the cell position for the building to be built in.
     * @param selection The enum corresponding to the class of building to make.
     * @return a boolean representing if the building is made successfully.
     */
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

        if (mapArray[position[0]][position[1]] != null ||
            (currentlyBuilding >= maxBuildsAtOnce && building.getTimeToBuild() != 0) ||
            counters.get(building.getClass()) >= maxPerBuildingType.get(building.getClass())) {
            return false;
        }

        counters.put(building.getClass(), counters.get(building.getClass()) + 1);

        Task buildingCreation = new BuildTask(building.getTimeToBuild() + timer.getGameTime(),
            building,
            this);
        timer.scheduleTask(buildingCreation);
        mapArray[position[0]][position[1]] = building;
        currentlyBuilding += 1;
        return true;
    }

    /**
     * To be called when a building is ready to be completed in its construction. It adds the building to the list of
     * buildings and then requests the map to render the building as complete, it also then shows the map graph as
     * having the given type of building in it, which is useful when building roads as opposed to other parts, so the
     * graph knows what buildings are traversable. It also connects the bus stops together if one is built as well as
     * applying all proximity boosters that need to be updated with the new construction. Finally, if applicable, it
     * sets up the scoring task schedule with the timer.
     * @param building The building to complete construction of.
     */
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
//            System.out.println("DISTANCE between " +
//            building.getClass().getSimpleName() +
//            " and " +
//            other.getClass().getSimpleName() +
//            " is: " +
//            graph.distanceBetween(graph.getNode(building), graph.getNode(other)));
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
        currentlyBuilding -= 1;
        //SET UP SCORE RECURRING TASK
        if (!(building instanceof  Road || building instanceof  Tree || building instanceof  Lake)) {
            BuildingScoreTask scoreTask = new BuildingScoreTask(timer.getGameTime(),
                building.getScore(),
                scorer,
                timer,
                building.getScoreFrequency(),
                building);
            timer.scheduleTask(scoreTask);
        }
    }

    /**
     * To be used internally to recalculate a building's modifier whenever the gamestate changes to affect it
     * @param building The building to recalculate the modifiers for.
     */
    private void calcModifier(BaseBuilding building) {
        building.resetModifier();
        addClosenessDebuff(building);
        addBooster(building);
    }

    /**
     * Applies the necessary debuff to any building within the given range of other buildings.
     * @param building The building to apply the closeness debuff to.
     */
    private void addClosenessDebuff(BaseBuilding building) {
        BuildingNode buildingNode = graph.getNode(building);
        for (BaseBuilding otherBuilding : buildings) {
            if (otherBuilding != building &&
                !(otherBuilding instanceof Road) &&
                graph.distanceBetween(buildingNode, graph.getNode(otherBuilding)) <= debuffDistance) {
                building.multModifier(debuffPotency);
                otherBuilding.multModifier(debuffPotency);
                break;
            }
        }
    }

    /**
     * Applies the necessary booster buffs to a building. It finds the closest booster building to the building
     * specified and adds a multiplier to the specified building's modifier based on their proximity and the booster
     * building's booster.
     * @param building The building to apply the booster to. (Should be a non-booster building right now)
     */
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
        if (closestBooster != null) {
            building.multModifier(Math.max(1 + (float) closestBooster.getBooster() / closestDist, 1.1f));
        }
    }

    public List<Integer> getCounter() {
        return Arrays.asList(counters.get(Halls.class),
            counters.get(Bar.class),
            counters.get(LectureTheatre.class),
            counters.get(Restaurant.class),
            counters.get(SportsHall.class),
            counters.get(BusStop.class),
            maxPerBuildingType.get(Halls.class),
            maxPerBuildingType.get(Bar.class),
            maxPerBuildingType.get(LectureTheatre.class),
            maxPerBuildingType.get(Restaurant.class),
            maxPerBuildingType.get(SportsHall.class),
            maxPerBuildingType.get(BusStop.class));
    }
}
