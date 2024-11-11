package io.github.NeonStudiosGame.timer;

import io.github.NeonStudiosGame.Scorer;
import io.github.NeonStudiosGame.buildings.BaseBuilding;

/**
 * This is a final implementation of ScoreTask, it stores a building in addition to the usual parts, it also implements
 * the getNextScoreTask to create a new task that recalculates the building score as it might've changed since the last
 * task was created.
 */
public class BuildingScoreTask extends ScoreTask {
    BaseBuilding building;

    /**
     * This mostly passes its parameters to the superclass except for the building which is stored by this constructor.
     * @param timeToPerform The game-time that the run method should be called.
     * @param scoreToAdd The score that this task should add to the scorer.
     * @param scorer The scorer to interface with to update score.
     * @param timer The timer to reschedule the task with.
     * @param frequency How often the task should be rescheduled.
     * @param building The building that the score should be pulled from.
     */
    public BuildingScoreTask(float timeToPerform,
                             int scoreToAdd,
                             Scorer scorer,
                             Timer timer,
                             int frequency,
                             BaseBuilding building) {
        super(timeToPerform, scoreToAdd, scorer, timer, frequency);
        this.building = building;
    }

    /**
     * Creates a new score task by adding the frequency to the time this task should've been performed (This should be
     * the current time), this gives the time that this new task should be performed. It also recalculates the score
     * that the building should give, as this may change throughout the game.
     * @return The new BuildingScoreTask to be scheduled with the timer.
     */
    @Override
    public ScoreTask getNextScoreTask() {
        float newTime = timeToPerform + frequency;
        return new BuildingScoreTask(newTime, building.getScore(), scorer, timer, frequency, building);
    }
}
