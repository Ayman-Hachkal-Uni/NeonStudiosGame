package io.github.NeonStudiosGame.timer;

import io.github.NeonStudiosGame.BuildMaster.BuildMaster;
import io.github.NeonStudiosGame.buildings.BaseBuilding;
import io.github.NeonStudiosGame.screens.GameScreen;

public class BuildTask extends Task {
    BaseBuilding building;
    GameScreen screen;
    BuildMaster buildMaster;
    public BuildTask(float timeToPerform, BaseBuilding building, GameScreen gameScreen, BuildMaster buildMaster) {
        super(timeToPerform);
        this.building = building;
        this.buildMaster = buildMaster;
        screen = gameScreen;
    }

    @Override
    public void run() {
        //System.out.println("BuildTask started");

        buildMaster.completeConstruction(building);
    }
}
