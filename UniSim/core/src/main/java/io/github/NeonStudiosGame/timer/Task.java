package io.github.NeonStudiosGame.timer;


/**
 * This class is to be used by the timer class, it's an abstract class that contains at least a timeToPerform, at the
 * game-time specified the overridden run method should be run.
 */
public abstract class Task {
    public float timeToPerform;

    /**
     * This is the constructor, it only sets the timeToPerform
     * @param timeToPerform The game-time that the run method should be called.
     */
    public Task(float timeToPerform) {
        this.timeToPerform = timeToPerform;
    }

    public abstract void run();
}
