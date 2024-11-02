package io.github.NeonStudiosGame.buildings;

public class Halls extends BaseBuilding{
    /**
     * This is the constructor for the Halls, just calls the constructor for BaseBuilding via super, and then sets the
     * time to build and texture locations to predetermined ones.
     * @param position should be an array of two integers [x,y]. These represent the cell in which the building is to be
     * placed.
     */
    public Halls(int[] position) {
        super(position);
        this.timeToBuild = 3;//0;
        this.buildingTextureLocation = buildingsFolder + "halls.png";
    }



}
