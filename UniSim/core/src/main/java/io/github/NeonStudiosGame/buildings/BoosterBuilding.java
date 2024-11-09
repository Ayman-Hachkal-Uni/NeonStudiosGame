package io.github.NeonStudiosGame.buildings;

public class BoosterBuilding extends BaseBuilding {
    private int booster;
    public BoosterBuilding(int[] position) {
        super(position);
        booster = 0;
    }

    public int getBooster() {
        return booster;
    }
}
