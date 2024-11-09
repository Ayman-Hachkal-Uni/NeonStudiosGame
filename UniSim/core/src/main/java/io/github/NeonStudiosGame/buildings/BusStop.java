package io.github.NeonStudiosGame.buildings;

public class BusStop extends BaseBuilding {

    public BusStop(int[] position) {
        super(position);
        this.timeToBuild = 3;//0;
        this.buildingTextureLocation = buildingsFolder + "bus_stop.png";
    }
}
