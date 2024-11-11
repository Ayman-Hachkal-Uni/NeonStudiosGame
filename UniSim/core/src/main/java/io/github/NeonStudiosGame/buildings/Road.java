package io.github.NeonStudiosGame.buildings;

public class Road extends BaseBuilding {
    /**
     * This is the constructor for the Road, just calls super on the BaseBuilding with the position, as well
     * as setting the timeToBuild to 0, the road should be used as a traversable path with no other functionality, it
     * shouldn't give the player score directly.
     * @param position should be an array of two integers [x,y]. These represent the cell in which the building is to be
     * placed.
     */
    public Road(int[] position) {
        super(position);
        timeToBuild = 0;
    }
}
