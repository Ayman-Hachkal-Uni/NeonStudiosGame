package io.github.NeonStudiosGame.buildings;

public class Lake extends BaseBuilding {
    /**
     * Lake right now functions purely as BaseBuilding, the class is used to distinguish it from player-made buildings,
     * although it could be updated easily if future requirements deem it necessary
     * @param position should be an array of two integers [x,y]. These represent the cell in which the building is to be
     * placed.
     */
    public Lake(int[] position) {
        super(position);
    }
}
