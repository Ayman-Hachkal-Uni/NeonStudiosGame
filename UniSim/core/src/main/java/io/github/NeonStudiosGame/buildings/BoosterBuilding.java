package io.github.NeonStudiosGame.buildings;

public class BoosterBuilding extends BaseBuilding {
    protected final int booster;
    /**
     * This is the constructor for the BoosterBuilding, just calls super on the BaseBuilding with the position, as well
     * as setting the booster to 5, this booster can be reassigned to subclasses and is the baseBoost for proximity
     * buffs.
     * @param position should be an array of two integers [x,y]. These represent the cell in which the building is to be
     * placed.
     */
    public BoosterBuilding(int[] position) {
        super(position);
        booster = 5;
    }

    public int getBooster() {
        return booster;
    }
}
