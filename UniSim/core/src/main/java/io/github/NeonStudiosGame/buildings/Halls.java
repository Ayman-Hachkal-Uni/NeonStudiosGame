package io.github.NeonStudiosGame.buildings;

public class Halls extends BaseBuilding{
    public Halls(int[] position) {
        super(position);
        this.timeToBuild = 30;
        this.buildingTextureLocation = buildingsFolder + "halls.png";
    }



}
