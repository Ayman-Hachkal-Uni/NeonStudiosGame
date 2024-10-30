package io.github.NeonStudiosGame.BuildMaster;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.NeonStudiosGame.buildings.*;

import java.util.LinkedList;
import java.util.List;


public class BuildMaster {
    List<BaseBuilding> buildings = new LinkedList<>();
    public BuildMaster() {

    }

    public void createBuilding(int[] position) {
        BuildingEnum selection = BuildingEnum.HALLS;
        BaseBuilding building = switch (selection) {
            case BASE_BUILDING -> new BaseBuilding(position);
            case HALLS -> new Halls(position);
            case LECTURE_THEATRE -> new LectureTheatre(position);
            case RESTAURANT -> new Restaurant(position);
            case SPORTS_HALL -> new SportsHall(position);
        };
        buildings.add(building);
    }

}
