package io.github.NeonStudiosGame.timer;

import io.github.NeonStudiosGame.Scorer;

/**
 * This is another abstract extension of task. It's to be used to update the score at explicit times, and potentially
 * given frequencies.
 */
public abstract class ScoreTask extends Task {
    protected int score;
    protected Scorer scorer;
    protected int frequency; // run task this every x seconds
    protected Timer timer;

    /**
     * This is the first constructor of the class, which doesn't include frequency; it assumes that the task will only
     * be run once. These parameters are directly passed to the other constructor setting timer to null and frequency to
     * Integer.MAX_VALUE.
     * @param timeToPerform The game-time that the run method should be called.
     * @param scoreToAdd The score that this task should add to the scorer.
     * @param scorer The scorer to interface with to update score.
     */
    public ScoreTask (float timeToPerform, int scoreToAdd, Scorer scorer) {
        this(timeToPerform, scoreToAdd, scorer, null, Integer.MAX_VALUE);
    }

    /**
     * This constructor passes the time to call the run method to the superclass, which in turn updates the property it
     * also sets the score to be added, the scorer that it should be added to, the timer that should be used to
     * reschedule this task, and the frequency to reschedule it at, i.e., how often this task should be rescheduled.
     * @param timeToPerform The game-time that the run method should be called.
     * @param scoreToAdd The score that this task should add to the scorer.
     * @param scorer The scorer to interface with to update score.
     * @param timer The timer to reschedule the task with.
     * @param frequency How often the task should be rescheduled.
     */
    public ScoreTask(float timeToPerform, int scoreToAdd, Scorer scorer, Timer timer, int frequency) {
        super(timeToPerform);
        this.score = scoreToAdd;
        this.scorer = scorer;
        this.frequency = frequency;
        this.timer = timer;
    }

    /**
     * To be called by the timer schedule at timeToPerform. It requests the scorer to update the score by the given
     * amount. Then it checks if it should be/can be rescheduled by checking if there's a timer to use and that the game
     * won't have ended before the task is run again. If it passes the conditions then it reschedules the task with the
     * timer by creating a new ScoreTask object with the getNextScoreTask method.
     */
    @Override
    public void run() {
        scorer.addScore(score);
        if (timeToPerform + frequency <= Timer.TOTAL_GAME_TIME && timer != null) {
            ScoreTask newScoreTask = getNextScoreTask();
            timer.scheduleTask(newScoreTask);
        }
    }

    /**
     * This should be implemented in order to create the next score task, this may be different per subclass based on
     * for example how the given score is generated.
     * @return A new score task to be rescheduled by the run method.
     */
    public abstract ScoreTask getNextScoreTask();
}
