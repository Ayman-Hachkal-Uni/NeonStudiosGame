package io.github.NeonStudiosGame.buildings;

public class Bar extends BoosterBuilding {
    /**
     * This is the constructor for the Bar, just calls the constructor for BoosterBuilding via super, and then sets the
     * time to build and texture locations to predetermined ones.
     * @param position should be an array of two integers [x,y]. These represent the cell in which the building is to be
     * placed.
     */
    public Bar(int[] position) {
        super(position);
        this.timeToBuild = 3;
        this.buildingTextureLocation = buildingsFolder + "bar.png";
    }
}
