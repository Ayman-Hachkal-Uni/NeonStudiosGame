package io.github.NeonStudiosGame.buildings;

public class Restaurant extends BaseBuilding{
    public Restaurant(int[] position) {
        super(position);
        this.timeToBuild = 30;
        this.buildingTextureLocation = buildingsFolder + "restaurant.png";
    }



}
