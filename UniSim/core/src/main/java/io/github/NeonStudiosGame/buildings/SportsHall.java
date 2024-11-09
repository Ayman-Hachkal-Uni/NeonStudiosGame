package io.github.NeonStudiosGame.buildings;

public class SportsHall extends BoosterBuilding{
    /**
     * This is the constructor for the SportsHall, just calls the constructor for BaseBuilding via super, and then sets the
     * time to build and texture locations to predetermined ones.
     * @param position should be an array of two integers [x,y]. These represent the cell in which the building is to be
     * placed.
     */
    public SportsHall(int[] position) {
        super(position);
        this.timeToBuild = 3;
        this.buildingTextureLocation = buildingsFolder + "sports_hall.png";
    }



}
