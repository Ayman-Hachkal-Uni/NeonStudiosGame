package io.github.NeonStudiosGame.timer;

import io.github.NeonStudiosGame.Scorer;
import io.github.NeonStudiosGame.buildings.BaseBuilding;

public class BuildingScoreTask extends ScoreTask {
    BaseBuilding building;
    public BuildingScoreTask(float timeToPerform,
                             int scoreToAdd,
                             Scorer scorer,
                             Timer timer,
                             int frequency,
                             BaseBuilding building) {
        super(timeToPerform, scoreToAdd, scorer, timer, frequency);
        this.building = building;
    }

    @Override
    public ScoreTask getNextScoreTask() {
        float newTime = timeToPerform + frequency;
        BuildingScoreTask task = new BuildingScoreTask(newTime, building.getScore(), scorer, timer, frequency, building);
        return task;
    }
}
