package io.github.NeonStudiosGame.timer;

import java.util.function.Function;

public abstract class Task {
    public float timeToPerform;

    public Task(float timeToPerform) {
        this.timeToPerform = timeToPerform;
    }

    public abstract void run();
}
