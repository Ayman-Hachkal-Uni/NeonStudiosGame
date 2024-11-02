package io.github.NeonStudiosGame.timer;

import io.github.NeonStudiosGame.buildings.BaseBuilding;
import io.github.NeonStudiosGame.screens.GameScreen;

public class BuildTask extends Task {
    BaseBuilding building;
    GameScreen screen;
    public BuildTask(float timeToPerform, BaseBuilding building, GameScreen gameScreen) {
        super(timeToPerform);
        this.building = building;
        screen = gameScreen;
    }

    @Override
    public void run() {
        //System.out.println("BuildTask started");
        screen.renderFullyCompletedBuilding(building);
        return;
    }
}
