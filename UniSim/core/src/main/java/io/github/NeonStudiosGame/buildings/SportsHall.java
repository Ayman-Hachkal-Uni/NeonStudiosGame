package io.github.NeonStudiosGame.buildings;

public class SportsHall extends BaseBuilding{
    public SportsHall(int[] position) {
        super(position);
        this.timeToBuild = 30;
        this.buildingTextureLocation = buildingsFolder + "sports_hall.png";
    }



}
