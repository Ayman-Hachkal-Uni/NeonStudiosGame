package io.github.NeonStudiosGame.BuildMaster;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.NeonStudiosGame.Scorer;
import io.github.NeonStudiosGame.screens.GameScreen;
import io.github.NeonStudiosGame.timer.BuildTask;
import io.github.NeonStudiosGame.timer.Task;
import io.github.NeonStudiosGame.timer.Timer;
import io.github.NeonStudiosGame.buildings.*;

import java.util.LinkedList;
import java.util.List;


public class BuildMaster {
    public Timer timer;
    private BaseBuilding[][] mapArray;
    private final List<BaseBuilding> buildings;
    private GameScreen gameScreen;
    private Scorer scorer;

    public BuildMaster(GameScreen gameScreen) {
        buildings = new LinkedList<>();
        this.gameScreen = gameScreen;
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
            case 14 -> new Tree(new int[] {x,y});
            default -> null;
        };
    }

    public void setMapArray(TiledMapTileLayer layer) {
        mapArray = new BaseBuilding[layer.getWidth()][layer.getHeight()];
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) {
                    mapArray[x][y] = null;
                    continue;
                }
                BaseBuilding building = getBuilding(cell.getTile().getId(), x, y);
                mapArray[x][y] = building;
            }
        }
    }

    public void print(Object anything) {
        System.out.println(anything);
    }
    public boolean createBuilding(int[] position, BuildingEnum selection) {
        BaseBuilding building = switch (selection) {
            case BASE_BUILDING -> new BaseBuilding(position);
            case HALLS -> new Halls(position);
            case LECTURE_THEATRE -> new LectureTheatre(position);
            case RESTAURANT -> new Restaurant(position);
            case SPORTS_HALL -> new SportsHall(position);
        };
        if (mapArray[position[0]][position[1]] != null) {
            return false;
        }


        Task buildingCreation = new BuildTask(building.getTimeToBuild() + timer.getGameTime(), building, gameScreen, this);
        timer.scheduleTask(buildingCreation);
        mapArray[position[0]][position[1]] = building;
        return true;
    }
    public void completeConstruction(BaseBuilding building) {
        buildings.add(building);
        gameScreen.renderFullyCompletedBuilding(building);

        //TEMP EXAMPLE SCORE ADDED

        scorer.addScore(200);
    }
}
