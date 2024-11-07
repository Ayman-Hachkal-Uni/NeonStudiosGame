package io.github.NeonStudiosGame.timer;

import io.github.NeonStudiosGame.Scorer;

public abstract class ScoreTask extends Task {
    protected int score;
    protected Scorer scorer;
    protected int frequency; // run task this every x seconds
    protected Timer timer;
    public ScoreTask(float timeToPerform, int scoreToAdd, Scorer scorer, Timer timer, int frequency) {
        super(timeToPerform);
        this.score = scoreToAdd;
        this.scorer = scorer;
        this.frequency = frequency;
        this.timer = timer;
    }

    @Override
    public void run() {
        scorer.addScore(score);
        if (timeToPerform + frequency < 300) {
            ScoreTask newScoreTask = getNextScoreTask();
            timer.scheduleTask(newScoreTask);
        }
    }

    public abstract ScoreTask getNextScoreTask();
}
